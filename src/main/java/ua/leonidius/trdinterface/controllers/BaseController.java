package ua.leonidius.trdinterface.controllers;

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

}
