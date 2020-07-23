package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ItemsListController extends BaseController {

    public ItemsListController(ScreenManager manager) {
        super(manager);
    }

    public abstract void selectItem(ShopItem item);

    public abstract ShopItem[] fetchItems();

    /**
     * Called in ItemListScreen's update() to refresh title
     * (e.g. after buyable items category rename)
     * @return title
     */
    public abstract String getTitle();

    public String buildItemButtonText(ShopItem item) {
        Item gameItem = item.toGameItem();

        StringBuilder sb = new StringBuilder();
        sb.append(gameItem.getName());
        sb.append(" (").append(item.getPrice())
                .append(Trading.settings.currency).append(")");

        if (gameItem.hasEnchantments()) {
            return TextFormat.colorize(TextFormat.DARK_PURPLE.getChar(), sb.toString());
        } else return sb.toString();
    }

}
