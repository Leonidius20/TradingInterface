package ua.leonidius.trdinterface;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static ua.leonidius.trdinterface.Trading.settings;

public abstract class ShopHelper {

    public static Map<Item, Double> getItemAndPrice(int itemRecordId) throws SQLException, IOException {
        String query = "SELECT id, price, nbt FROM buy_items WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, itemRecordId);
        ResultSet results = statement.executeQuery();
        results.next();
        Item item = Item.fromString(results.getString("id"));
        byte[] nbtBytes = results.getBytes("nbt");
        if (nbtBytes != null && nbtBytes.length != 0) {
            CompoundTag nbt = NBTIO.read(nbtBytes);
            item.setCompoundTag(nbt);
        }
        double price = results.getDouble("price");

        results.close();
        statement.close();

        LinkedHashMap<Item, Double> map = new LinkedHashMap<>();
        map.put(item, price);
        return map;
    }

    public static Item getItem(int itemRecordId) throws SQLException, IOException {
        String query = "SELECT id, nbt FROM buy_items WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, itemRecordId);
        ResultSet results = statement.executeQuery();
        results.next();
        Item item = Item.fromString(results.getString("id"));
        byte[] nbtBytes = results.getBytes("nbt");

        results.close();
        statement.close();

        if (nbtBytes != null && nbtBytes.length != 0) {
            CompoundTag nbt = NBTIO.read(nbtBytes);
            item.setCompoundTag(nbt);
        }

        return item;
    }

    public static String buildDescription(int itemRecordId) throws SQLException, IOException {
        StringBuilder contentBuilder = new StringBuilder();

        Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemRecordId);
        Item item = itemAndPrice.keySet().iterator().next();
        double priceWithoutDiscount = itemAndPrice.get(item);
        double price = priceWithoutDiscount; // TODO: apply discount

        contentBuilder.append(Message.WDW_BUY_NAME.getText(item.getName()));
        if (!item.getCustomName().equals("")) {
            contentBuilder.append("\n")
                    .append(Message.WDW_BUY_ORIGINAL_NAME.getText(item.getName(), item.getId(), item.getDamage()));
        }
        contentBuilder.append("\n").append(Message.WDW_BUY_PRICE.getText(price, settings.currency));
            /*if (shopItem.discount != 0) {
                contentBuilder.append("\n")
                        .append(Message.WDW_BUY_DISCOUNT.getText(shopItem.discount, shopItem.calculatePrice(), settings.currency));
            }*/
        if (item.getLore().length != 0) {
            StringBuilder loreBuilder = new StringBuilder();
            int index = -1;
            for (String line : item.getLore()) {
                index++;
                if (index != item.getLore().length - 1) loreBuilder.append(line);
            }
            contentBuilder.append("\n")
                    .append(Message.WDW_BUY_CUSTOM_LORE.getText(loreBuilder.toString()));
        }
        Enchantment[] enchantments = item.getEnchantments();
        if (enchantments != null && enchantments.length != 0) {
            contentBuilder.append("\n");
            StringBuilder enchListBuilder = new StringBuilder();
            for (int i = 0; i < enchantments.length; i++) {
                Enchantment enchantment = enchantments[i];
                enchListBuilder.append(enchantment.getName()).append(" ").append(enchantment.getLevel());
                if (i != enchantments.length - 1) enchListBuilder.append(", ");
            }
            contentBuilder.append(Message.WDW_BUY_ENCHANTMENTS.getText(enchListBuilder.toString()));
        }
        return contentBuilder.toString();
    }

    public static String getBuyCategoryName(int categoryId) throws SQLException {
        String nameQuery = "SELECT name FROM categories WHERE record_id = ?";
        PreparedStatement nameStatement = Trading.getDbConnection().prepareStatement(nameQuery);
        nameStatement.setInt(1, categoryId);
        ResultSet nameResults = nameStatement.executeQuery();
        nameResults.next();
        String name = nameResults.getString("name");

        nameResults.close();
        nameStatement.close();

        return name;
    }

    public static void addBuyItem(int shopId, int categoryId, Item item, double price) throws SQLException, IOException {
        String query = "INSERT INTO buy_items(shop_id, category_id, id, price, nbt) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        String id = item.getId() + ":" + item.getDamage();
        byte[] nbtBytes = item.getNamedTag() == null ? null : NBTIO.write(item.getNamedTag());
        statement.setInt(1, shopId);
        statement.setInt(2, categoryId);
        statement.setString(3, id);
        statement.setDouble(4, price);
        statement.setBytes(5, nbtBytes);
        statement.executeUpdate();
        statement.close();
    }

    public static void deleteBuyItem(int itemId) throws SQLException {
        String query = "DELETE FROM buy_items WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, itemId);
        statement.executeUpdate();
        statement.close();
    }

    public static void addBuyEnchantment(int itemId, int id, int level) throws SQLException, IOException {
        String query1 = "SELECT id, nbt FROM buy_items WHERE record_id = ?";
        PreparedStatement statement1 = Trading.getDbConnection().prepareStatement(query1);
        statement1.setInt(1, itemId);
        ResultSet result = statement1.executeQuery();
        result.next();
        Item item = Item.fromString(result.getString("id"));
        byte[] nbtBytes = result.getBytes("nbt");
        statement1.close();
        if (nbtBytes != null && nbtBytes.length != 0) {
            CompoundTag nbt1 = NBTIO.read(nbtBytes);
            item.setCompoundTag(nbt1);
        }

        Enchantment enchantment = Enchantment.get(id).setLevel(level);
        item.addEnchantment(enchantment);

        byte[] newNbtBytes = NBTIO.write(item.getNamedTag()); // certainly not null

        String query2 = "UPDATE buy_items SET nbt = ? WHERE record_id = ?";
        PreparedStatement statement2 = Trading.getDbConnection().prepareStatement(query2);
        statement2.setBytes(1, newNbtBytes);
        statement2.setInt(2, itemId);
        statement2.executeUpdate();
        statement2.close();
    }

    public static void removeBuyEnchantment(int itemId, Enchantment enchantment) throws SQLException, IOException {
        Item item = getItem(itemId);

        Item newItem = Item.fromString(item.getId() + ":" + item.getDamage());
        if (!item.getCustomName().equals("")) newItem.setCustomName(item.getCustomName());
        if (item.getLore() != null && item.getLore().length != 0) {
            newItem.setLore(item.getLore());
        }


        for (Enchantment enchantment1 : item.getEnchantments()) {
            if (enchantment1.getId() != enchantment.getId()) {
                newItem.addEnchantment(enchantment1);
            }
        }

        byte[] newNbtBytes = newItem.getNamedTag() == null ? null : NBTIO.write(newItem.getNamedTag());

        String query = "UPDATE buy_items SET nbt = ? WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setBytes(1, newNbtBytes);
        statement.setInt(2, itemId);
        statement.executeUpdate();
        statement.close();
    }

    public static void renameCategory(int categoryId, String newName) throws SQLException {
        String query = "UPDATE categories SET name = ? WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setString(1, newName);
        statement.setInt(2, categoryId);
        statement.executeUpdate();
        statement.close();
    }

    public static void deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM categories WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, categoryId);
        statement.executeUpdate();
        statement.close();
    }

    public static void addCategory(int shopId, String categoryName) throws SQLException {
        String query = "INSERT INTO categories(shop_id, name) VALUES(?, ?)";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, shopId);
        statement.setString(2, categoryName);
        statement.executeUpdate();
        statement.close();
    }

}