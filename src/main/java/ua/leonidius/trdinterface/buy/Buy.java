package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 26.09.17.
 */
public abstract class Buy {

    public static void buy(Player player, int amount, int shopId, int categoryId, int itemId) {
        try {
            String query = "SELECT * FROM buy_items WHERE record_id = ?";
            PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
            statement.setInt(1, itemId);
            ResultSet itemEntry = statement.executeQuery();
            itemEntry.next();
            String id = itemEntry.getString("id");
            Item item = Item.fromString(id);
            byte[] nbtBytes = itemEntry.getBytes("nbt");
            if (nbtBytes != null && nbtBytes.length != 0) {
                CompoundTag nbt = NBTIO.read(nbtBytes);
                item.setCompoundTag(nbt);
            }
            item.setCount(amount);

            // Getting price with discount applied
            double priceWithoutDiscount = itemEntry.getDouble("price");
            double price = priceWithoutDiscount; // TODO: apply discount
            double cost = amount * price;
            EconomyAPI.getInstance().reduceMoney(player, cost);
            player.getInventory().addItem(item);

            // Success
            String name = item.getName();

            if (settings.transactionLogging) {
                Message.LOG_BOUGHT.log(player.getName(), amount, name, item.getId() + ":" + item.getDamage(), cost, settings.currency);
            }

            player.showFormWindow(new BuySuccessScreen(amount, name, cost, shopId, categoryId));
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            player.showFormWindow(new BuyFailScreen(3, shopId, categoryId));
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