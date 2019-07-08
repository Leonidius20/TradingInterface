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

    public static String buildItemButtonText(Item item, double price) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getName());

        for (Enchantment enchantment : item.getEnchantments()) {
            sb.append(getEnchanmentSymbol(enchantment));
        }

        sb.append("(").append(price).append(settings.currency).append(")");

        return sb.toString();
    }

    private static String getEnchanmentSymbol(Enchantment enchantment) {
        switch (enchantment.getId()) {
            case Enchantment.ID_PROTECTION_ALL: // 0
            case Enchantment.ID_PROTECTION_FIRE: // 1
            case Enchantment.ID_PROTECTION_FALL: // 2
            case Enchantment.ID_PROTECTION_EXPLOSION:
            case Enchantment.ID_PROTECTION_PROJECTILE:
            case Enchantment.ID_THORNS:
            case Enchantment.ID_WATER_BREATHING: // 6
            case Enchantment.ID_WATER_WALKER:
            case Enchantment.ID_WATER_WORKER: // 8
            case Enchantment.ID_DAMAGE_ALL:
            case Enchantment.ID_DAMAGE_SMITE:
            case Enchantment.ID_DAMAGE_ARTHROPODS: // 11
            case Enchantment.ID_KNOCKBACK:
                return parseUTF8("\\u2753"); // placeholder
            case Enchantment.ID_FIRE_ASPECT:
                return parseUTF8("\\uD83D\\uDD25"); // fire
            case Enchantment.ID_LOOTING:
            case Enchantment.ID_EFFICIENCY: // 15
            case Enchantment.ID_SILK_TOUCH: // 16
            case Enchantment.ID_DURABILITY:
            case Enchantment.ID_FORTUNE_DIGGING:
            case Enchantment.ID_BOW_POWER:
            case Enchantment.ID_BOW_KNOCKBACK:
            case Enchantment.ID_BOW_FLAME:
            case Enchantment.ID_BOW_INFINITY:
            case Enchantment.ID_FORTUNE_FISHING:
            case Enchantment.ID_LURE: // 24
                return parseUTF8("\\u2753"); // placeholder
            case Enchantment.ID_FROST_WALKER:
                return parseUTF8("\\u2744"); // snowflake
            case Enchantment.ID_MENDING:
            case Enchantment.ID_BINDING_CURSE:
            case Enchantment.ID_VANISHING_CURSE:
            case Enchantment.ID_TRIDENT_IMPALING:
            case Enchantment.ID_TRIDENT_RIPTIDE: // 30
            case Enchantment.ID_TRIDENT_LOYALTY:
            case Enchantment.ID_TRIDENT_CHANNELING:
            default:
                return parseUTF8("\\u2753"); // placeholder
        }
    }

    public static String getBuyCategoryName(int categoryId) throws SQLException {
        String nameQuery = "SELECT name FROM categories WHERE record_id = ?";
        PreparedStatement nameStatement = Trading.getDbConnection().prepareStatement(nameQuery);
        nameStatement.setInt(1, categoryId);
        ResultSet nameResults = nameStatement.executeQuery();
        nameResults.next();
        return nameResults.getString("name");
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

        byte[] newNbtBytes = NBTIO.write(item.getNamedTag());

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

        byte[] newNbtBytes = NBTIO.write(newItem.getNamedTag());

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

    /*
     * Based on  loadConvert method of java.util.Properties
     * Stolen from Fromgate's Smiley plugin. Must be re-implemented by me
     */
    public static String parseUTF8(String instr) {
        char [] in = instr.toCharArray();
        int len = in.length;
        int off = 0;
        char[] convtBuf = new char[len];
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;
        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if(aChar == 'u') {
                    // Read the xxxx
                    int value=0;
                    for (int i=0; i<4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0': case '1': case '2': case '3': case '4':
                            case '5': case '6': case '7': case '8': case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a': case 'b': case 'c':
                            case 'd': case 'e': case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A': case 'B': case 'C':
                            case 'D': case 'E': case 'F':

                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char)value;
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = aChar;
            }
        }
        return new String (out, 0, outLen);
    }

}