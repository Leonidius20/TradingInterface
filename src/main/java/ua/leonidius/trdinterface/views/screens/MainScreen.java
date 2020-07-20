package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.buy.categories.BuyCategoriesScreen;

import java.sql.SQLException;

public class MainScreen extends SimpleScreen {

    private transient int shopId;

    public MainScreen(ScreenManager manager, int shopId) {
        super(manager, Message.WDW_MAIN_TITLE.getText(), "");

        this.shopId = shopId;

        addButton(new ElementButton(Message.MENU_BUY.getText()));
        addButton(new ElementButton(Message.MENU_SELL.getText()));

        /*if (hasPermission) {
            //addButton(new ElementButton(Message.MENU_EDIT_BUY_LIST.getText()));
            //addButton(new ElementButton(Message.MENU_EDIT_SELL_LIST.getText()));
            addButton(new ElementButton(Message.MENU_CUSTOM_NAMES.getText()));
        }*/
        // TODO: if we remove the custom translation of names, we can remove the hasPermission argument from the constructor
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();

        switch (clickedButtonId) {
            case 0:  // Buy button
                try {
                    SimpleScreen window = new BuyCategoriesScreen(getScreenManager(), shopId);
                    getScreenManager().addAndShow(window);
                } catch (SQLException e) {
                    if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                    getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
                }
                break;
            case 1:  // Sell button
                break;
            case 2:
                //event.getPlayer().showFormWindow(new CustomNamesScreen());
                break;
            /*case 2:  // Edit buyable items button
                event.getPlayer().showFormWindow(new EditCategoriesScreen(shopId));
                break;
            case 3:  // Edit sellable items button
                event.getPlayer().showFormWindow(new EditSellListScreen(shopId));
                break;
            case 4:  // Custom item names
                event.getPlayer().showFormWindow(new CustomNamesScreen(shopId));
                // TODO: remove that feature
                break;
                */
        }
    }

    @Override
    public void update() {}

}