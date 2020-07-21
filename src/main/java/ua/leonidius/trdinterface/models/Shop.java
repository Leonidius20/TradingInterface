package ua.leonidius.trdinterface.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Domain model for a shop
 */
@DatabaseTable(tableName = "shops")
public class Shop {

    public Shop() {}

    public Shop(String name) {
        this.name = name;
    }

    public static Shop getDefault() {
        Shop defaultShop = new Shop("default");
        defaultShop.recordId = 1;
        return defaultShop;
    }

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId;

    @DatabaseField(canBeNull = false, unique = true)
    String name;

    @ForeignCollectionField(columnName = "buyable_items_categories", foreignFieldName = "shop")
    public ForeignCollection<Category> buyableItemsCategories;
    // Do we need columnName here?

    @ForeignCollectionField(columnName = "sellable_items", foreignFieldName = "shop")
    public ForeignCollection<SellableItem> sellableItems;

}
