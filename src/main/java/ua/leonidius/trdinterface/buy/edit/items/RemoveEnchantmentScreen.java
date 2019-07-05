package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.BuyManageItemScreen;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveEnchantmentScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId, itemId;

    public RemoveEnchantmentScreen(Player player, int shopId, int categoryId, int itemId) {
        super("", ""); // TODO: add title

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        try {
            Item item = ShopHelper.getItem(itemId);

            for (Enchantment enchantment : item.getEnchantments()) {
                addButton(new ElementButton(enchantment.getName() + " " + enchantment.getLevel()));
            }

        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }

            closed = true;
            player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));

        }
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        try {
            int index = getResponse().getClickedButtonId();
            Item item = ShopHelper.getItem(itemId);
            Enchantment enchantment = item.getEnchantments()[index];
            ShopHelper.removeBuyEnchantment(itemId, enchantment);

            if (Trading.settings.editLogging) {
                Message.LOG_BUY_ENCHANTMENT_REMOVED.log(player, enchantment.getName(), enchantment.getLevel(),
                        item.getName(), item.getId(), item.getDamage());
            }

            player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
            // TODO: error screen
           player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));
        }
    }

}