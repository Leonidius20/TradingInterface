package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class AmountSelectorController extends ItemDetailsViewController {

    public AmountSelectorController(ScreenManager manager) {
        super(manager);
    }

    public abstract void selectAmount(int amount);

}
