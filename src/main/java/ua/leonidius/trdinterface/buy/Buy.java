package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 26.09.17.
 */
public abstract class Buy {

    public static void buy (Player player, String categoryId, String id, int amount){
        Item item = Item.fromString(id);
        item.setCount(amount);

        // Getting price with discount applied
        double price = getPrice(categoryId, id);

        double cost = amount*price;
        EconomyAPI.getInstance().reduceMoney(player, cost);
        player.getInventory().addItem(item);

        // Success
        String name = ItemName.get(id);

        if (settings.transactionLogging) {
            Message.LOG_BOUGHT.log(player.getName(), amount, name, id, cost, settings.currency);
        }

        player.showFormWindow(new BuySuccessScreen(name, amount, cost, categoryId));
    }

    // Getting price and applying discount
    public static double getPrice (String categoryId, String id) {
        String key = id.replace(":", "-");
        ConfigSection itemSection = buyCfg.getSection(categoryId).getSection("items").getSection(key);

        double priceWithoutDiscount = itemSection.getDouble("price");

        if (itemSection.exists("discount")) {
            double discountPercent = itemSection.getDouble("discount");
            double discount = (priceWithoutDiscount * discountPercent) / 100;
            return priceWithoutDiscount - discount;
        } else {
            return priceWithoutDiscount;
        }

    }

    public static int getMaxByMoney(Player player, double price){
        double money = EconomyAPI.getInstance().myMoney(player);
        return (int)Math.floor(money/price);
    }

    public static int getMaxByInventory(Player player, Item item){
        PlayerInventory inventory = player.getInventory();
        int maxStack = item.getMaxStackSize();
        int amount = 0;
        for (int i=0; i < 36; i++){
            Item itemInSlot = inventory.getItem(i);
            if (itemInSlot.getId() == Item.AIR){
                amount = amount + maxStack;
            } else if (itemInSlot.getId() == item.getId() && itemInSlot.getDamage() == item.getDamage()){
                amount = amount +  (maxStack-itemInSlot.getCount());
            }
        }
        return amount;
    }

}