package ua.leonidius.trdinterface;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ShopEditor {

    public static void addBuyItem(int shopId, int categoryId, Item item, double price) throws SQLException, IOException {
        String query = "INSERT INTO buy_items(shop_id, category_id, id, price, nbt) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        String id = item.getId() + ":" + item.getDamage();
        byte[] nbtBytes = NBTIO.write(item.getNamedTag());
        statement.setInt(1, shopId);
        statement.setInt(2, categoryId);
        statement.setString(3, id);
        statement.setDouble(4, price);
        statement.setBytes(5, nbtBytes);
        statement.executeUpdate();
    }

    public static void renameCategory(int categoryId, String newName) throws SQLException {
        String query = "UPDATE categories SET name = ? WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setString(1, newName);
        statement.setInt(2, categoryId);
        statement.executeUpdate();
    }

    public static void deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM categories WHERE record_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, categoryId);
        statement.executeUpdate();
    }

    public static void addCategory(int shopId, String categoryName) throws SQLException {
        String query = "INSERT INTO categories(shop_id, name) VALUES(?, ?)";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, shopId);
        statement.setString(2, categoryName);
        statement.executeUpdate();
    }

}