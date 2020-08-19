package ua.leonidius.trdinterface.utils;

import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.models.Shop;

import java.sql.SQLException;
import java.util.List;

/**
 * Contains methods to add items from the old
 * YAML item lists to the new database, as
 * well as import custom item name translations
 */
public abstract class YamlToDbConverter {

    public static boolean convertBuyableItems(Config buyableItems) {
        try {
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);

            int numberOfCategories = 0;
            int numberOfItems = 0;

            for (String categoryKey : buyableItems.getKeys(false)) {
                numberOfCategories++;

                ConfigSection category = buyableItems.getSection(categoryKey);

                String categoryName = category.getString("name");

                Category categoryModel;
                List<Category> eponymousCategories =
                        categoryDao.queryForEq("name", categoryName);
                if (eponymousCategories.size() == 0) {
                    categoryModel = new Category(Shop.getDefault(), categoryName);
                    categoryDao.create(categoryModel);
                } else categoryModel = eponymousCategories.get(0);

                for (String itemKey : category.getSection("items").getKeys(false)) {
                    numberOfItems++;

                    ConfigSection item = category.getSection("items").getSection(itemKey);

                    String id = itemKey.replace("-", ":");
                    if (Item.fromString(id).getId() == 0) continue;
                    double price = item.getDouble("price");

                    BuyableItem itemModel = new BuyableItem(Shop.getDefault(),
                            categoryModel, id, price);

                    categoryModel.items.add(itemModel);

                    categoryModel.items.getDao()
                            .assignEmptyForeignCollection(itemModel, "discounts");

                    if (item.exists("discount")) {
                        double percent = item.getDouble("discount");
                        Discount discount = new Discount(itemModel, percent, -1); // infinite
                        itemModel.addDiscount(discount);
                    }
                }
            }

            Message.LOG_BUYABLE_ITEMS_IMPORTED.log(numberOfCategories, numberOfItems);

            return true;
        } catch (SQLException e) {
            Trading.printException(e);
            return false;
        }
    }

    public static boolean convertSellableItems(Config sellableItems) {
        // TODO

        Trading.getPlugin().getLogger().info("");
        return true;
    }

    public static boolean convertTranslations(Config translations) {
        return true;
    }

}
