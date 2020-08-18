package ua.leonidius.trdinterface.sell;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 22.12.17.
 */

public abstract class Sell {

    public static void sellItem(Player player, String id, int amount) {
        Item item = Item.fromString(id);
        item.setCount(amount);

        double price = Trading.sellCfg.getDouble(id.replace(":", "-"));

        double cost = price * amount;

        if (!player.getInventory().contains(item)) {
            player.showFormWindow(new SellFailScreen());
            return;
        }

        player.getInventory().removeItem(item);
        EconomyAPI.getInstance().addMoney(player, cost);

        // Success
        String name = ItemName.get(id);

        if (settings.transactionLogging) {
            Message.LOG_SOLD.log(player.getName(), amount, name, id, cost, settings.currency);
        }

        player.showFormWindow(new SellSuccessScreen(name, amount, cost));
    }

    public static int getItemCount(PlayerInventory inventory, Item item){
        int amount = 0;
        for (int i=0; i < 36; i++){
            Item itemInSlot = inventory.getItem(i);
            if (itemInSlot.getId() == item.getId() && itemInSlot.getDamage() == item.getDamage()){
                amount = amount + itemInSlot.getCount();
            }
        }
        return amount;
    }

}