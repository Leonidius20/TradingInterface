package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.BuyManageItemScreen;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.SQLException;

public class AddEnchantmentScreen extends FormWindowCustom implements Screen {

    private int shopId, categoryId, itemId;

    public AddEnchantmentScreen(int shopId, int categoryId, int itemId) {
        super(""); // TODO: add title

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        ElementDropdown dropdown = new ElementDropdown("SELECT ENCHANTMENT"); // TODO: translate

        for (Enchantment enchantment : Enchantment.getEnchantments()) {
            dropdown.addOption(enchantment.getName());
        }

        addElement(dropdown); // 0

        addElement(new ElementSlider("SELECT LEVEL (0 to return)", 0, 5, 1, 0)); // 1
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        int enchantmentLevel = (int)getResponse().getSliderResponse(1);

        if (enchantmentLevel == 0) { // Going back
            player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));
            return;
        }

        int enchantmentId = getResponse().getDropdownResponse(0).getElementID();

        try {
            ShopHelper.addBuyEnchantment(itemId, enchantmentId, enchantmentLevel);

            if (Trading.settings.editLogging) {
                String enchName = Enchantment.get(enchantmentId).getName();
                Item item = ShopHelper.getItem(itemId);
                Message.LOG_BUY_ENCHANTMENT_ADDED.log(player, enchName, enchantmentLevel,
                        item.getName(), item.getId(), item.getDamage());
            }

            player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
            player.showFormWindow(new AddEnchantmentFailScreen(shopId, categoryId, itemId));
        }
    }

}