package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemDetailsEditController extends BaseController {

    public ItemDetailsEditController(ScreenManager manager) {
        super(manager);
    }

    public abstract void submitDetails(String itemId, String priceS,
                                       String customName, String customLore);

}
