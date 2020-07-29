package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemsListController extends ListController<ShopItem> {

    public ItemsListController(ScreenManager manager) {
        super(manager);
    }

    @Override
    public String buildItemButtonText(ShopItem item) {
        Item gameItem = item.toGameItem();

        String result;

        if (item instanceof BuyableItem && ((BuyableItem) item).getDiscount() != null) {
            result = Message.BTN_ITEM_BUY_SALE.getText(item.getName(),
                    item.getPrice(), Trading.getSettings().getCurrency(),
                    ((BuyableItem) item).getDiscount().getPercent());
        } else {
            result = Message.BTN_ITEM_BUY.getText(item.getName(), item.getPrice(),
                    Trading.getSettings().getCurrency());
        }

        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), result);
        } else return result;
    }

}
