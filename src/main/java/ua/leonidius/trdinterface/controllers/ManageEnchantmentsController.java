package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;

public class ManageEnchantmentsController extends BaseController {

    private final ShopItem item;

    public ManageEnchantmentsController(ScreenManager manager, ShopItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {

    }
}
