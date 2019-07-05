package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.edit.items.AddEnchantmentScreen;
import ua.leonidius.trdinterface.buy.edit.items.RemoveEnchantmentScreen;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This screen is shown to players who have permissions to manage the shop. It allows to select an action
 * to perform on an item: buy, edit, delete, edit discount,
 */
public class BuyManageItemScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId, itemId;

    public BuyManageItemScreen(Player player, int shopId, int categoryId, int itemId) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText(), "");

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        try {
            setContent(ShopHelper.buildDescription(itemId));

            addButton(new ElementButton(Message.BTN_BACK.getText())); // 0
            addButton(new ElementButton(Message.BTN_BUY_ITEM.getText())); // 1
            addButton(new ElementButton(Message.BTN_EDIT_ITEM.getText())); // 2
            addButton(new ElementButton(Message.BTN_EDIT_DISCOUNT.getText())); // 3
            addButton(new ElementButton(Message.BTN_ADD_ENCHANTMENT.getText())); // 4
            addButton(new ElementButton(Message.BTN_REMOVE_ENCHANTMENT.getText())); // 5
            addButton(new ElementButton(Message.BTN_DELETE_ITEM.getText())); // 6
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        switch (getResponse().getClickedButtonId()) {
            case 0: // Back
                player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId,
                        player.hasPermission("shop.edit")));
                break;
            case 1: // Buy
                try {
                    String query = "SELECT id, price, nbt FROM buy_items WHERE record_id = ?";
                    PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
                    statement.setInt(1, itemId);
                    ResultSet results = statement.executeQuery();
                    results.next();
                    Item item = Item.fromString(results.getString("id"));
                    byte[] nbtBytes = results.getBytes("nbt");
                    if (nbtBytes != null && nbtBytes.length != 0) {
                        CompoundTag nbt = NBTIO.read(nbtBytes);
                        item.setCompoundTag(nbt);
                    }
                    double priceWithoutDiscount = results.getDouble("price");
                    double price = priceWithoutDiscount; // TODO: apply discount

                    int maxByMoney = Buy.getMaxByMoney(player, price);
                    int maxByInventory = Buy.getMaxByInventory(player, item);

                    if (maxByMoney == 0 && maxByInventory == 0) {
                        player.showFormWindow(new BuyFailScreen(0, shopId, categoryId));
                    } else if (maxByMoney == 0) {
                        player.showFormWindow(new BuyFailScreen(1, shopId, categoryId));
                    } else if (maxByInventory == 0) {
                        player.showFormWindow(new BuyFailScreen(2, shopId, categoryId));
                    } else {
                        int maxAmount = Math.min(maxByInventory, maxByMoney);
                        player.showFormWindow(new BuySelectAmountScreen(player, shopId, categoryId, itemId, maxAmount));
                    }
                } catch (SQLException | IOException e) {
                    Trading.getPlugin().getLogger().error(e.getMessage());
                    player.showFormWindow(new BuyFailScreen(3, shopId, categoryId));
                }
                break;
            case 2: // Edit
                break;
            case 3: // Edit discount
                break;
            case 4: // Add enchantment
                player.showFormWindow(new AddEnchantmentScreen(shopId, categoryId, itemId));
                break;
            case 5: // Remove enchantment
                player.showFormWindow(new RemoveEnchantmentScreen(player, shopId, categoryId, itemId));
                break;
            case 6: // Delete item
                break;
        }
    }

}