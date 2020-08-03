package ua.leonidius.trdinterface.controllers.buy.items;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.DeleteCategoryController;
import ua.leonidius.trdinterface.controllers.buy.categories.edit.RenameCategoryController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.AddBuyableItemController;
import ua.leonidius.trdinterface.controllers.buy.items.edit.ManageBuyableItemController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.LinkedHashMap;

public class BuyableItemsController extends ListController<BuyableItem> {

    private final Category category;

    public BuyableItemsController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_RENAME_CATEGORY.getText(), this::renameCategory);
            buttons.put(Message.BTN_DELETE_CATEGORY.getText(), this::deleteCategory);
            buttons.put(Message.BTN_ADD_ITEM.getText(), this::addItem);
        }

        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_BUY_EMPTY_CAT.getText(), buttons));
    }

    @Override
    public BuyableItem[] fetchItems() {
        BuyableItem[] items = category.items.toArray(new BuyableItem[0]);

        for (BuyableItem item : items) {
            Discount discount = item.getDiscount();
            if (discount == null) continue;
            // for infinite discounts, copied from old yaml configs
            if (discount.getEndTime() == -1) continue;
            if (discount.getEndTime() <= (System.currentTimeMillis() / 1000)) {
                item.removeDiscount();
            }
        }

        return items;
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
    public void selectItem(BuyableItem item) {
        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            new ManageBuyableItemController(manager, item).showScreen();
            return;
        }

        new BuyAmountSelectorController(manager, item).showScreen();
    }

    @Override
    public String buildItemButtonText(BuyableItem item) {
        Item gameItem = item.toGameItem();

        String result;

        if (item.getDiscount() != null) {
            result = Message.BTN_ITEM_BUY_SALE.getText(item.getName(),
                    item.getPrice(), Trading.getSettings().getCurrency(),
                    item.getDiscount().getPercent());
        } else {
            result = Message.BTN_ITEM_BUY.getText(item.getName(), item.getPrice(),
                    Trading.getSettings().getCurrency());
        }


        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), result);
        } else return result;
    }

}
