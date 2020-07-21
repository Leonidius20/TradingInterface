package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

/**
 * Universal success/error window that allows to go back to the previous window.
 */
public class InfoScreen extends SimpleScreen {

    public InfoScreen(InfoController controller, String title, String message) {
        super(title, message);
        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));
    }

    @Override
    public void update() {}

}