package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.MainController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

public class MainScreen extends SimpleScreen {

    public MainScreen(MainController controller) {
        super(Message.WDW_MAIN_TITLE.getText(), "");

        addButton(new CallbackButton(Message.MENU_BUY.getText(), controller::buy));
        addButton(new CallbackButton(Message.MENU_SELL.getText(), controller::sell));

        /*if (manager.getPlayer().hasPermission...) {
            //addButton(new ElementButton(Message.MENU_EDIT_BUY_LIST.getText()));
            //addButton(new ElementButton(Message.MENU_EDIT_SELL_LIST.getText()));
            addButton(new ElementButton(Message.MENU_CUSTOM_NAMES.getText()));
        }*/

        // TODO: add converter button
    }

    @Override
    public void update() {}

}