package ua.leonidius.trdinterface.controllers.buy;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.InfoScreen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 26.09.17.
 */
public abstract class Buy {

    public static void buy(Player player, ScreenManager manager, int amount, int itemId) {
        try {
            Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemId);
            Item item = itemAndPrice.keySet().iterator().next();
            double priceWithoutDiscount = itemAndPrice.get(item);
            item.setCount(amount);

            // Getting price with discount applied
            double price = priceWithoutDiscount; // TODO: apply discount
            double cost = amount * price;
            EconomyAPI.getInstance().reduceMoney(player, cost);
            player.getInventory().addItem(item);

            // Success
            String name = item.getName();

            if (settings.transactionLogging) {
                Message.LOG_BOUGHT.log(player.getName(), amount, name, item.getId() + ":" + item.getDamage(), cost, settings.currency);
            }

            String message = Message.BUY_SUCCESS.getText(amount, name, cost, Trading.settings.currency);
            manager.addAndShow(new InfoScreen(manager, message));
        } catch (SQLException | IOException e) {
            if (settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            manager.addAndShow(new InfoScreen(manager, Message.ERROR.getText()));
        }
    }

    public static int getMaxByMoney(Player player, double price) {
        double money = EconomyAPI.getInstance().myMoney(player);
        return (int)Math.floor(money/price);
    }

    // TODO: test this
    public static int getMaxByInventory(Player player, Item item) {
        PlayerInventory inventory = player.getInventory();
        int maxStack = item.getMaxStackSize();
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            Item itemInSlot = inventory.getItem(i);
            if (itemInSlot.getId() == Item.AIR) {
                amount = amount + maxStack;
            } else if (itemInSlot.equals(item, true, true)) {
                amount += (maxStack - itemInSlot.getCount());
            }
        }
        return amount;
    }

}