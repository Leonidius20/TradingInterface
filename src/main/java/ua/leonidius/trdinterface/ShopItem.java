package ua.leonidius.trdinterface;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShopItem {

    public String id;
    public double price;
    public double discount = 0; // in percents
    public List<ShopEnchantment> enchantments = null;
    public String customName = null;
    public String customLore = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public List<ShopEnchantment> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(List<ShopEnchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomLore() {
        return customLore;
    }

    public void setCustomLore(String customLore) {
        this.customLore = customLore;
    }

    public String toString() {
        return new Yaml().dumpAs(this, Tag.MAP, null);
    }

    public static ShopItem fromString(String string) {
        return (ShopItem)new Yaml(new Constructor(ShopItem.class)).load(string);
    }

    @SuppressWarnings("unchecked")
    public static ShopItem fromMap(Map map) {
        ShopItem shopItem = new ShopItem();
        shopItem.id = (String)map.get("id");
        shopItem.price = (Double)map.get("price");
        if (map.containsKey("discount")) shopItem.discount = (Double)map.get("discount");
        else shopItem.discount = 0;
        if (map.containsKey("customName")) shopItem.customName = (String)map.get("customName");
        else shopItem.customName = null;
        if (map.containsKey("customLore")) shopItem.customLore = (String)map.get("customLore");
        else shopItem.customLore = null;
        List<ShopEnchantment> enchantments = new LinkedList<ShopEnchantment>();
        if (map.containsKey("enchantments")) {
            List<Map<String, Integer>> enchantmentsAsMaps = (List<Map<String, Integer>>)map.get("enchantments");
            for (Map<String, Integer> enchantmentAsMap : enchantmentsAsMaps) {
                ShopEnchantment enchantment = new ShopEnchantment();
                enchantment.id = enchantmentAsMap.get("id");
                enchantment.level = enchantmentAsMap.get("level");
                enchantments.add(enchantment);
            }
        }
        shopItem.enchantments = enchantments;
        return shopItem;
    }

    // For testing
    public static ShopItem fromStream(InputStream stream) {
        return (ShopItem)new Yaml(new Constructor(ShopItem.class)).load(stream);
    }

    public Item getItem() {
        Item item = Item.fromString(id);
        item.setCustomName(customName);
        item.setLore(customLore);

        for (ShopEnchantment shopEnchantment : enchantments) {
            Enchantment enchantment = Enchantment.getEnchantment(shopEnchantment.id).setLevel(shopEnchantment.level);
            item.addEnchantment(enchantment);
        }

        return item;
    }

    /**
     * @return A name to display on an ItemButton
     */
    public String getName() {
        return customName == null ? Item.fromString(id).getName() : customName;
    }

    public double calculatePrice() {
        if (discount == 0) return price;
        double difference = (price / 100) * discount;
        return price - difference;
    }

    public static class ShopEnchantment {
        public int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int level;
    }

}