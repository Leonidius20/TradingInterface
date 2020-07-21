package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

/**
 * Universal success/error window that allows to go back to the previous window.
 */
public class InfoScreen extends SimpleScreen {

    public InfoScreen(ScreenManager manager, String message) {
        super("", message);
        addButton(new CallbackButton(Message.BTN_BACK.getText(), manager::back));
    }

    @Override
    public void update() {}

}