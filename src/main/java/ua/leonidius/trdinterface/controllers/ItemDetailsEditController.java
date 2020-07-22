package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemDetailsEditController extends BaseController {

    public ItemDetailsEditController(ScreenManager manager) {
        super(manager);
    }

    public abstract void submitDetails(String itemId, String priceS,
                                       String customName, String customLore);

    protected void showErrorScreen() {
        new InfoController(manager, Message.ERROR.getText()).showScreen();
    }

    protected void showErrorScreen(String message) {
        new InfoController(manager, Message.ERROR_DESC.getText(message))
                .showScreen();
    }

}
