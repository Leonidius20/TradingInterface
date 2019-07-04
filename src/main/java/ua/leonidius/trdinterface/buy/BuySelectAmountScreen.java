package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public class BuySelectAmountScreen extends FormWindowCustom implements Screen {

    private int shopId, categoryId, itemId;

    public BuySelectAmountScreen(Player player, int shopId, int categoryId, int itemId, int maxAmount) {
        //super(Message.WDW_BUY_TITLE.getText(name));
        super("");

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        try {
            String query = "SELECT * FROM buy_items WHERE record_id = ?";
            PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
            statement.setInt(1, itemId);
            ResultSet itemEntry = statement.executeQuery();
            itemEntry.next();
            String id = itemEntry.getString("id");
            Item item = Item.fromString(id);
            byte[] nbtBytes = itemEntry.getBytes("nbt");
            if (nbtBytes != null && nbtBytes.length != 0) {
                CompoundTag nbt = NBTIO.read(nbtBytes);
                item.setCompoundTag(nbt);
            }

            String name = item.getName();
            double priceWithoutDiscount = itemEntry.getDouble("price");
            double price = priceWithoutDiscount; // TODO: apply discount
            setTitle(item.getName());

            addElement(new ElementLabel(Message.WDW_BUY_NAME.getText(name))); // 0
            addElement(new ElementLabel(Message.WDW_BUY_PRICE.getText(price, Trading.settings.currency))); // 1
            addElement(new ElementLabel(Message.WDW_BUY_ORIGINAL_NAME.getText(Item.fromString(id).getName()))); // 2
            StringBuilder sb = new StringBuilder();
            String enchantmentsText;
            Enchantment[] enchantments = item.getEnchantments();
            if (enchantments == null || enchantments.length == 0) {
                enchantmentsText = Message.WDW_BUY_ENCHANTMENTS_ABSENT.getText(); // 3
            } else {
                for (int i = 0; i < enchantments.length; i++) {
                    Enchantment enchantment = enchantments[i];
                    sb.append(enchantment.getName()).append(" ").append(enchantment.getLevel());
                    if (i != enchantments.length - 1) sb.append(", ");
                }
                enchantmentsText = sb.toString();
            }
            addElement(new ElementLabel(Message.WDW_BUY_ENCHANTMENTS.getText(enchantmentsText))); // 3
            addElement(new ElementSlider(Message.WDW_BUY_AMOUNT.getText(), 0, maxAmount, 1, 1)); // 4
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            player.showFormWindow(new BuyFailScreen(3, shopId, categoryId));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int amount = (int)getResponse().getSliderResponse(4);

        if (amount == 0) {
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        }

        Buy.buy(player, amount, shopId, categoryId, itemId);
    }

}