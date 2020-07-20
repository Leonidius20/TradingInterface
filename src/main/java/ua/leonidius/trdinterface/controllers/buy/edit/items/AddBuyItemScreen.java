package ua.leonidius.trdinterface.controllers.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.CustomScreen;
import ua.leonidius.trdinterface.views.screens.InfoScreen;

import java.io.IOException;
import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyItemScreen extends CustomScreen {

    private transient int shopId, categoryId;

    public AddBuyItemScreen(ScreenManager manager, int shopId, int categoryId) {
        super(manager, Message.WDW_ADD_ITEM_TITLE.getText());

        this.shopId = shopId;
        this.categoryId = categoryId;

        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText())); // 0
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText())); // 1
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_NAME.getText())); // 2
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_LORE.getText())); // 3
        addElement(new ElementLabel(Message.WDW_ADD_ITEM_ENCHANTMENTS.getText())); // 4
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            Item item = Item.fromString(getResponse().getInputResponse(0));

            double price = Double.parseDouble(getResponse().getInputResponse(1).replace(",", "."));

            String customName = getResponse().getInputResponse(2);
            if (customName != null && !customName.equals("")) item.setCustomName(customName);

            String customLore = getResponse().getInputResponse(3);
            if (customLore != null && !customLore.equals("")) item.setLore(customLore);
            // TODO: split the lore string into different lines (separate with \n)

            ShopHelper.addBuyItem(shopId, categoryId, item, price);

            if (settings.editLogging) {
                if (customName == null || customName.equals("")) {
                    Message.LOG_BUY_ITEM_ADDED.log(event.getPlayer().getName(), item.getName(),
                            item.getId() + ":" + item.getDamage(), price, settings.currency);
                } else {
                    Message.LOG_BUY_ITEM_ADDED_WITH_CUSTOM_NAME.log(event.getPlayer().getName(), customName,
                            Item.fromString(item.getId() + ":" + item.getDamage()).getName(),
                            item.getId() + ":" + item.getDamage(), price, settings.currency);
                }
            }

            String message = Message.WDW_ADD_BUY_ITEM_SUCCESS.getText(item.getName());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
        } catch (SQLException dbe) {
            if (settings.debugMode) Trading.getPlugin().getLogger().error(dbe.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        } catch (NumberFormatException | IOException e) {
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.WDW_INVALID_PARAMS.getText()));
        }
    }

}