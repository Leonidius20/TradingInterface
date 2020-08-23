package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemDetailsEditScreen;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

public class EditItemController extends ItemDetailsEditController {

    private final ShopItem item;

    public EditItemController(ScreenManager manager, ShopItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ItemDetailsEditScreen(this,
                Message.WDW_EDIT_ITEM_TITLE.getText(),
                item.getItemId(), String.valueOf(item.getPurePrice()),
                item.toGameItem().getCustomName(),
                arrayToString(item.toGameItem().getLore())), true);
    }

    @Override
    public void submitDetails(String itemId, String priceS,
                              String customName, String customLore) {
        String oldId = item.getItemId();
        String oldName = item.getName();
        String oldCustomName = item.toGameItem().getCustomName();
        double oldPrice = item.getPurePrice();
        String oldLore = arrayToString(item.toGameItem().getLore());
        Enchantment[] oldEnchantments = item.toGameItem().getEnchantments();

        try {
            ShopItem.populate(item, itemId, priceS, customName,
                    customLore, oldEnchantments);
        } catch (IllegalArgumentException e) { // invalid input
            showErrorScreen(e.getMessage());
            return;
        }

        // so that item.getName(), item.getLore() return updated info
        item.resetGameItem();

        String newLore = arrayToString(item.toGameItem().getLore());

        if (item.getItemId().equals(oldId)
                && item.getPurePrice() == oldPrice
                && customName.equals(oldCustomName)
                && newLore.equals(oldLore)) {
            manager.back();
            return;
        }

        try {
            item.update();
        } catch (SQLException e) {
            handleException(e);
            return;
        }

        if (Trading.getSettings().logEdits()) {
            LinkedList<String> changes = new LinkedList<>();

            if (!item.getItemId().equals(oldId)) {
                changes.add(Message.LOG_EDITED_ID.getText(oldId, item.getItemId()));
            }

            if (item.getPrice() != oldPrice) {
                changes.add(Message.LOG_EDITED_PRICE.getText(oldPrice,
                        Trading.getSettings().getCurrency(), item.getPurePrice()));
            }

            if (!customName.equals(oldCustomName)) {
                if (oldCustomName.isEmpty()) {
                    changes.add(Message.LOG_ADDED_CUSTOM_NAME.getText(customName));
                } else if (customName.isEmpty()) {
                    changes.add(Message.LOG_REMOVED_CUSTOM_NAME.getText(oldCustomName));
                } else
                    changes.add(Message.LOG_EDITED_NAME.getText(
                            oldCustomName, customName));
            }

            if (!newLore.equals(oldLore)) {
                if (oldLore.trim().isEmpty()) {
                    changes.add(Message.LOG_ADDED_LORE.getText(newLore));
                } else if (newLore.trim().isEmpty()) {
                    changes.add(Message.LOG_REMOVED_LORE.getText(oldLore));
                } else changes.add(Message.LOG_EDITED_LORE.getText(oldLore, newLore));
            }

            if (item instanceof BuyableItem) {
                Message.LOG_BUY_ITEM_EDITED.log(manager.getPlayer().getName(),
                        oldName, listToString(changes));
            } else {
                Message.LOG_SELL_ITEM_EDITED.log(manager.getPlayer().getName(),
                        oldName, listToString(changes));
            }

        }

        manager.back();
    }

    /**
     * Joins an array of strings into a string, divided by line break
     *
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
     *
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
