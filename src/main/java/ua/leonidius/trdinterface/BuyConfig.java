package ua.leonidius.trdinterface;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.util.List;

public class BuyConfig {

    int formatVersion;

    public int getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    List<Shop> shops;

    public String serialize() {
        return new Yaml().dumpAs(this, Tag.MAP, null);
    }

    public static BuyConfig load() throws FileNotFoundException {
        InputStream stream = new FileInputStream(new File(Trading.getPlugin().getDataFolder(), "buyList.yml"));
        return (BuyConfig)new Yaml(new Constructor(ShopItem.class)).load(stream);
    }

    public void save() throws IOException {
        String dump =  new Yaml().dumpAs(this, Tag.MAP, null);
        File file = new File(Trading.getPlugin().getDataFolder(), "buyList.yml");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(dump);
        bw.close();
    }

    public class Shop {

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ShopCategory> getCategories() {
            return categories;
        }

        public void setCategories(List<ShopCategory> categories) {
            this.categories = categories;
        }

        String id;
        List<ShopCategory> categories;

    }

    public class ShopCategory {

        public String name;
        public List<ShopItem> items;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ShopItem> getItems() {
            return items;
        }

        public void setItems(List<ShopItem> items) {
            this.items = items;
        }
    }

}