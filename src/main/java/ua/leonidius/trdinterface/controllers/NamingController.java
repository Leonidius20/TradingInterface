package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;

/**
 * This is a base class for a controller for NamingScreen
 */
public abstract class NamingController extends BaseController {

    NamingController(ScreenManager manager) {
        super(manager);
    }

    public abstract void giveName(String name);

    public void back() {
        manager.back();
    }

}
