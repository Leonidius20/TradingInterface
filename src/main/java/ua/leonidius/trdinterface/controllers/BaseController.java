package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class BaseController {

    protected final ScreenManager manager;

    public BaseController(ScreenManager manager) {
        this.manager = manager;
    }

    public abstract void showScreen();

    public void back() {
        manager.back();
    }

    protected void showErrorScreen() {
        new InfoController(manager, Message.ERROR.getText()).showScreen();
    }

    protected void showErrorScreen(String message) {
        new InfoController(manager, Message.ERROR_DESC.getText(message)).showScreen();
    }

    /**
     * Prints exception info to console if debug mode is on
     * and shows an error screen to the user
     *
     * @param e exception to handle
     */
    protected void handleException(Exception e) {
        Trading.printException(e);
        showErrorScreen();
    }

}
