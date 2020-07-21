package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemDetailsController extends BaseController {

    public ItemDetailsController(ScreenManager manager) {
        super(manager);
    }

    public abstract void submitDetails(String itemId, String priceS,
                                       String customName, String customLore);

    protected void showErrorScreen() {
        new InfoController(manager, Message.ERROR.getText()).showScreen();
    }

}
