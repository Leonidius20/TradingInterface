package ua.leonidius.trdinterface.controllers.sell;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.CloseableIterator;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ItemsListController;
import ua.leonidius.trdinterface.controllers.sell.edit.EditSellableItemsController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.utils.ItemCompare;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

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
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.MENU_EDIT_SELL_LIST.getText(), this::editList);
        }

        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_SELL_NOTHING.getText(), buttons));
    }

    @Override
    public void selectItem(ShopItem item) {
        new SellAmountSelectorController(manager, (SellableItem) item).showScreen();
    }

    @Override
    public SellableItem[] fetchItems() {
        LinkedList<SellableItem> items = new LinkedList<>();
        CloseableIterator<SellableItem> iterator = shop.sellableItems.closeableIterator();
        // TODO: optimize
        while (iterator.hasNext()) {
            SellableItem item = iterator.next();
            for (int i = 0; i < 36; i++) {
                Item itemInSlot = manager.getPlayer().getInventory().getItem(i);
                if (ItemCompare.equals(itemInSlot, item.toGameItem())) {
                    items.add(item);
                }
            }
        }
        return items.toArray(new SellableItem[0]);
    }

    @Override
    public String getTitle() {
        return Message.WDW_SELL_SELECT_ITEM.getText();
    }

    public void editList() {
        new EditSellableItemsController(manager, shop).showScreen();
    }

}
