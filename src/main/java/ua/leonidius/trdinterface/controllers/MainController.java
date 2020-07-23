package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.controllers.buy.categories.CategoriesController;
import ua.leonidius.trdinterface.controllers.sell.SellableItemsController;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.MainScreen;

public class MainController extends BaseController {

    private final Shop shop;

    public MainController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new MainScreen(this));
    }

    public void buy() {
        new CategoriesController(manager, shop).showScreen();
    }

    public void sell() {
        new SellableItemsController(manager, shop).showScreen();
    }

}
