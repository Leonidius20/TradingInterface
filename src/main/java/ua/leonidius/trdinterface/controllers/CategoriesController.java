package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.BuyCategoriesScreen;

public class CategoriesController {

    private final Shop shop;
    private final ScreenManager manager;

    public CategoriesController(ScreenManager manager, Shop shop) {
        this.manager = manager;
        this.shop = shop;
    }

    public void showScreen() {
        manager.addAndShow(new BuyCategoriesScreen(this));
    }

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    public Category[] fetchCategories() {
        Category[] categories = new Category[0];
        shop.buyableItemsCategories.toArray(categories);
        return categories;
    }

    public void addCategory() {
        // TODO
        //manager.addAndShow(new AddCategoryScreen(getScreenManager(), shopId), true);
    }

    public void selectCategory(int categoryId) {
        // TODO
        /*try {
            BuyItemSelectorScreen screen = new BuyItemSelectorScreen(getScreenManager(), shopId, button.getCategoryId());
            getScreenManager().addAndShow(screen);
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }*/
    }

    public void back() {
        manager.back();
    }

}
