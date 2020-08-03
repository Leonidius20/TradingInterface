package ua.leonidius.trdinterface.controllers.buy.categories;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.AddCategoryController;
import ua.leonidius.trdinterface.controllers.buy.items.BuyableItemsListController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.LinkedHashMap;

public class CategoriesListController extends ListController<Category> {

    private final Shop shop;

    public CategoriesListController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_ADD_CATEGORY.getText(), this::addCategory);
        }

        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_BUY_NO_CATEGORIES.getText(), buttons));
    }

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    @Override
    public Category[] fetchItems() {
        Category[] categories = new Category[0];
        return shop.buyableItemsCategories.toArray(categories);
    }

    @Override
    public String buildItemButtonText(Category category) {
        return category.name;
    }

    @Override
    public String getTitle() {
        return Message.WDW_BUY_CAT_TITLE.getText();
    }

    public void addCategory() {
        new AddCategoryController(manager, shop).showScreen();
    }

    @Override
    public void selectItem(Category category) {
        new BuyableItemsListController(manager, category).showScreen();
    }

}
