package ua.leonidius.trdinterface.controllers.buy.edit.items.enchantment;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.CustomScreen;
import ua.leonidius.trdinterface.views.screens.InfoScreen;

import java.io.IOException;
import java.sql.SQLException;

public class AddEnchantmentScreen extends CustomScreen {

    private transient int itemId;

    public AddEnchantmentScreen(ScreenManager manager, int itemId) {
        super(manager, Message.WDW_ADD_ENCHANTMENT_TITLE.getText());

        this.itemId = itemId;

        ElementDropdown dropdown = new ElementDropdown(Message.WDW_ADD_ENCHANTMENT_SELECTOR_HINT.getText());

        for (Enchantment enchantment : Enchantment.getEnchantments()) {
            dropdown.addOption(enchantment.getName());
        }

        addElement(dropdown); // 0

        addElement(new ElementSlider(Message.WDW_ADD_ENCHANTMENT_LEVEL_HINT.getText(),
                0, 5, 1, 0)); // 1
    }

    @Override
    public void update() {}

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        int enchantmentLevel = (int)getResponse().getSliderResponse(1);

        if (enchantmentLevel == 0) { // Going back
            getScreenManager().back();
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

            getScreenManager().back();
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}