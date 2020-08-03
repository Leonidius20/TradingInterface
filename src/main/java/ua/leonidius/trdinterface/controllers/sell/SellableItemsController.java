package ua.leonidius.trdinterface.controllers.sell;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import com.j256.ormlite.dao.CloseableIterator;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.controllers.sell.edit.EditSellableItemsController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.utils.ItemCompare;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class SellableItemsController extends ListController<SellableItem> {

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
    public void selectItem(SellableItem item) {
        new SellAmountSelectorController(manager, item).showScreen();
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

    @Override
    public String buildItemButtonText(SellableItem item) {
        Item gameItem = item.toGameItem();

        int amount = -1; // TODO: real amount
        String result = Message.BTN_ITEM_SELL.getText(item.getName(),
                item.getPrice(), Trading.getSettings().getCurrency(), amount);


        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), result);
        } else return result;

    }

}
