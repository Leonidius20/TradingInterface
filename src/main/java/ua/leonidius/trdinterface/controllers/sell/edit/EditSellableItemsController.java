package ua.leonidius.trdinterface.controllers.sell.edit;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ItemsListController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemsListScreen;

import java.util.LinkedHashMap;

public class EditSellableItemsController extends ItemsListController {

    private final Shop shop;

    public EditSellableItemsController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ItemsListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_ADD_ITEM.getText(), this::addItem);
        }

        manager.addAndShow(new ItemsListScreen(this,
                Message.WDW_BUY_EMPTY_CAT.getText(), buttons)); // TODO: change text
    }

    @Override
    public void selectItem(ShopItem item) {
        new ManageSellableItemController(manager, (SellableItem) item).showScreen();
    }

    private void addItem() {
        new AddSellableItemController(manager, shop).showScreen();
    }

    @Override
    public SellableItem[] fetchItems() {
        return shop.sellableItems.toArray(new SellableItem[0]);
    }

    @Override
    public String getTitle() {
        return Message.MENU_EDIT_SELL_LIST.getText();
    }

}
