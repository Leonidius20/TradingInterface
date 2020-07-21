package ua.leonidius.trdinterface.controllers.buy.categories;

import ua.leonidius.trdinterface.controllers.buy.items.BuyableItemsController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.AddCategoryController;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.categories.BuyCategoriesScreen;

public class CategoriesController extends BaseController {

    private final Shop shop;

    public CategoriesController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new BuyCategoriesScreen(this));
    }

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    public Category[] fetchCategories() {
        Category[] categories = new Category[0];
        return shop.buyableItemsCategories.toArray(categories);
    }

    public void addCategory() {
        new AddCategoryController(manager, shop).showScreen();
    }

    public void selectCategory(Category category) {
        new BuyableItemsController(manager, category).showScreen();
    }

}
