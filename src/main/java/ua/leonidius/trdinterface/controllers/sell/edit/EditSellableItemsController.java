package ua.leonidius.trdinterface.controllers.sell.edit;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Controller for showing a list of all
 * sellable items in the shop to edit them
 */
public class EditSellableItemsController extends ListController<SellableItem> {

    private final Shop shop;

    public EditSellableItemsController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_ADD_ITEM.getText(), this::addItem);
        }

        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_BUY_EMPTY_CAT.getText(), buttons)); // TODO: change text
    }

    @Override
    public void selectItem(SellableItem item) {
        new ManageSellableItemController(manager, item).showScreen();
    }

    private void addItem() {
        new AddSellableItemController(manager, shop).showScreen();
    }

    @Override
    public Collection<SellableItem> fetchItems() {
        return shop.sellableItems;
    }

    @Override
    public String getTitle() {
        return Message.MENU_EDIT_SELL_LIST.getText();
    }

    @Override
    public String buildItemButtonText(SellableItem item) {
        Item gameItem = item.toGameItem();

        String result = Message.BTN_ITEM_BUY.getText(item.getName(),
                item.getPrice(), Trading.getSettings().getCurrency());

        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), result);
        } else return result;
    }

}
