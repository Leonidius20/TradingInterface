package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.NamingScreen;

/**
 * This is a base class for a controller for NamingScreen
 */
public abstract class NamingController extends BaseController {

    public NamingController(ScreenManager manager) {
        super(manager);
    }

    protected abstract String getScreenTitle();

    protected abstract String getInputFieldHint();

    protected String getDefaultText() {
        return "";
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new NamingScreen(this,
                getScreenTitle(), getInputFieldHint(),
                getDefaultText()), true);
    }

    public abstract void submitName(String name);

}
