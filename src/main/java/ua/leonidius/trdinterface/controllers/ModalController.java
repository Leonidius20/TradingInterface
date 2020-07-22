package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ModalController extends BaseController {

    public ModalController(ScreenManager manager) {
        super(manager);
    }

    public abstract void onPositiveResponse();

    public void onNegativeResponse() {
        manager.back();
    }

}
