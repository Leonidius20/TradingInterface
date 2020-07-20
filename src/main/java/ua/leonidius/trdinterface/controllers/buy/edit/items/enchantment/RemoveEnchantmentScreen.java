package ua.leonidius.trdinterface.controllers.buy.edit.items.enchantment;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.InfoScreen;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveEnchantmentScreen extends SimpleScreen {

    private transient int itemId;

    public RemoveEnchantmentScreen(ScreenManager manager, int itemId) throws SQLException, IOException {
        super(manager, Message.WDW_REMOVE_ENCHANTMENT_TITLE.getText(),
                Message.WDW_REMOVE_ENCHANTMENT_HINT.getText());

        this.itemId = itemId;

        Item item = ShopHelper.getItem(itemId);

        for (Enchantment enchantment : item.getEnchantments()) {
            addButton(new ElementButton(enchantment.getName() + " " + enchantment.getLevel()));
        }
    }

    @Override
    public void update() {}

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            int index = getResponse().getClickedButtonId();
            Item item = ShopHelper.getItem(itemId);
            Enchantment enchantment = item.getEnchantments()[index];
            ShopHelper.removeBuyEnchantment(itemId, enchantment);

            if (Trading.settings.editLogging) {
                Message.LOG_BUY_ENCHANTMENT_REMOVED.log(event.getPlayer().getName(), enchantment.getName(),
                        enchantment.getLevel(), item.getName(), item.getId(), item.getDamage());
            }

            getScreenManager().back();
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}