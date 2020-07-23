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

    protected void handleException(Exception e) {
        handleException(e, false);
    }

    /**
     *
     * @param e exception to handle
     * @param showMessage whether to show exception message in InfoScreen
     */
    protected void handleException(Exception e, boolean showMessage) {
        if (Trading.settings.debugMode) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            e.printStackTrace();
        }
        if (showMessage) showErrorScreen(e.getMessage());
        else showErrorScreen();
    }

}
