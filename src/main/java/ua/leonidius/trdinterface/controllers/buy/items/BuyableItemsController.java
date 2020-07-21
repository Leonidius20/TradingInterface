package ua.leonidius.trdinterface.controllers.buy.items;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.DeleteCategoryController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.RenameCategoryController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.AddBuyableItemController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.ManageBuyableItemController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.BuyableItemsScreen;

public class BuyableItemsController extends BaseController {

    private final Category category;

    public BuyableItemsController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new BuyableItemsScreen(this));
    }

    public String getCategoryName() {
        return category.name;
    }

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

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

    public void buyItem(BuyableItem item) {
        if (showEditingButtons()) { // TODO: check divided permissions
            new ManageBuyableItemController(manager, item).showScreen();
            return;
        }

        new BuyAmountSelectorController(manager, item).showScreen();
    }

    public String buildItemButtonText(BuyableItem item) {
        Item gameItem = item.toGameItem();

        StringBuilder sb = new StringBuilder();
        sb.append(gameItem.getName());
        sb.append(" (").append(item.price)
                .append(Trading.settings.currency).append(")");

        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), sb.toString());
        } else return sb.toString();
    }

}
