package ua.leonidius.trdinterface.controllers.sell;

import com.j256.ormlite.dao.CloseableIterator;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ItemsListController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemsListScreen;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SellableItemsController extends ItemsListController {

    private final Shop shop;

    public SellableItemsController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ItemsListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.MENU_EDIT_SELL_LIST.getText(), this::editList);
        }

        manager.addAndShow(new ItemsListScreen(this,
                Message.WDW_SELL_NOTHING.getText(), buttons));
    }

    @Override
    public void selectItem(ShopItem item) {
        // TODO:
    }

    @Override
    public SellableItem[] fetchItems() {
        LinkedList<SellableItem> items = new LinkedList<>();
        CloseableIterator<SellableItem> iterator = shop.sellableItems.closeableIterator();
        while (iterator.hasNext()) {
            SellableItem item = iterator.next();
            if (manager.getPlayer().getInventory().contains(item.toGameItem())) {
                items.add(item);
            }
        }
        return items.toArray(new SellableItem[0]);
    }

    @Override
    public String getTitle() {
        return Message.WDW_SELL_SELECT_ITEM.getText();
    }

    public void editList() {
        // TODO: open a list with all available items
    }

    @Override
    public boolean isBuyable() {
        return false;
    }

}
