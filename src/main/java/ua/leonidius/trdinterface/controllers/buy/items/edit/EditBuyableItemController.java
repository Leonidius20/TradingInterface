package ua.leonidius.trdinterface.controllers.buy.items.edit;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ItemDetailsEditController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemDetailsEditScreen;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

// TODO: make it a universal buyable/sellable controller
public class EditBuyableItemController extends ItemDetailsEditController {

    private final BuyableItem item;

    public EditBuyableItemController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        StringBuilder customLoreBuilder = new StringBuilder();
        String[] lore = item.toGameItem().getLore();

        for (int i = 0; i < lore.length; i++) {
            customLoreBuilder.append(lore[i]);
            if (i != lore.length - 1) customLoreBuilder.append("\n");
        }

        manager.addAndShow(new ItemDetailsEditScreen(this,
                Message.WDW_EDIT_ITEM_TITLE.getText(),
                item.getItemId(), String.valueOf(item.getPrice()),
                item.toGameItem().getCustomName(),
                customLoreBuilder.toString()));
    }

    @Override
    public void submitDetails(String itemId, String priceS, String customName, String customLore) {
        String oldId = item.getItemId();
        String oldName = item.getName();
        double oldPrice = item.getPrice();
        String oldLore = arrayToString(item.toGameItem().getLore());

        ShopItem.populate(item, itemId, priceS, customName, customLore);

        // so that item.getName(), item.getLore() return updated info
        item.resetGameItem();

        String newLore = arrayToString(item.toGameItem().getLore());

        if (item.getItemId().equals(oldId) && item.getPrice() == oldPrice
                && item.getName().equals(oldName)
                && newLore.equals(oldLore)) {
            manager.back();
            return;
        }

        try {
            Dao<BuyableItem, Integer> itemDao =
                    DaoManager.createDao(Trading.getSource(), BuyableItem.class);
            itemDao.update(item);
        } catch (SQLException e) {
            if (Trading.settings.debugMode)
                Trading.getPlugin().getLogger().error(e.getMessage());
            showErrorScreen();
            return;
        }

        if (Trading.settings.editLogging) {
            LinkedList<String> changes = new LinkedList<>();
            if (!item.getItemId().equals(oldId)) {
                changes.add(Message.LOG_EDITED_ID.getText(oldId, item.getItemId()));
            }
            if (item.getPrice() != oldPrice) {
                changes.add(Message.LOG_EDITED_PRICE.getText(oldPrice,
                        Trading.settings.currency, item.getPrice()));
            }
            if (!item.getName().equals(oldName)) {
                changes.add(Message.LOG_EDITED_NAME.getText(oldName, item.getName()));
            }
            if (!newLore.equals(oldLore)) {
                if (oldLore.trim().isEmpty()) {
                    changes.add(Message.LOG_ADDED_LORE.getText(newLore));
                } else changes.add(Message.LOG_EDITED_LORE.getText(oldLore, newLore));
            }

            Message.LOG_BUY_ITEM_EDITED.log(manager.getPlayer().getName(),
                    oldName, listToString(changes));
        }

        manager.back();
    }

    /**
     * Joins an array of strings into a string, divided by line break
     * @param array array of strings
     * @return resulting string
     */
    private static String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) sb.append('\n');
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * Joins a list of strings into a string, divided by commas
     * @param list list of strings
     * @return resulting string
     */
    private static String listToString(LinkedList<String> list) {
        StringBuilder sb = new StringBuilder();
        int length = list.size();
        Iterator<String> iterator = list.iterator();
        for (int i = 0; i < length; i++) {
            sb.append(iterator.next());
            if (i != length - 1) sb.append(", ");
        }
        return sb.toString();
    }

}
