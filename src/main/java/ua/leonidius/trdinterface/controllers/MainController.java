package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.controllers.buy.categories.CategoriesListController;
import ua.leonidius.trdinterface.controllers.sell.SellableItemsListController;
import ua.leonidius.trdinterface.controllers.translations.TranslationsListController;
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
        new CategoriesListController(manager, shop).showScreen();
    }

    public void sell() {
        new SellableItemsListController(manager, shop).showScreen();
    }

    public boolean showCustomNamesButton() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    public void customNames() {
        new TranslationsListController(manager).showScreen();
    }

}
