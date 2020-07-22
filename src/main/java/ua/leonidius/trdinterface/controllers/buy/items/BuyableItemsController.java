package ua.leonidius.trdinterface.controllers.buy.items;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ItemsListController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.DeleteCategoryController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.RenameCategoryController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.AddBuyableItemController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.ManageBuyableItemController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemsListScreen;

import java.util.LinkedHashMap;

public class BuyableItemsController extends ItemsListController {

    private final Category category;

    public BuyableItemsController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ItemsListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_RENAME_CATEGORY.getText(), this::renameCategory);
            buttons.put(Message.BTN_DELETE_CATEGORY.getText(), this::deleteCategory);
            buttons.put(Message.BTN_ADD_ITEM.getText(), this::addItem);
        }

        manager.addAndShow(new ItemsListScreen(this,
                Message.WDW_BUY_EMPTY_CAT.getText(), buttons));
    }

    @Override
    public BuyableItem[] fetchItems() {
        BuyableItem[] items = new BuyableItem[0];
        return category.items.toArray(items);
    }

    public void renameCategory() {
        new RenameCategoryController(manager, category).showScreen();
    }

    public void deleteCategory() {
        new DeleteCategoryController(manager, category).showScreen();
    }

    public void addItem() {
       new AddBuyableItemController(manager, category).showScreen();
    }

    @Override
    public String getTitle() {
        return category.name;
    }

    @Override
    public void selectItem(ShopItem item) {
        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            new ManageBuyableItemController(manager, (BuyableItem) item).showScreen();
            return;
        }

        new BuyAmountSelectorController(manager, (BuyableItem) item).showScreen();
    }

    @Override
    public boolean isBuyable() {
        return true;
    }

}
