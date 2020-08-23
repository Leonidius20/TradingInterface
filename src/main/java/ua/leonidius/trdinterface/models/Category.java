package ua.leonidius.trdinterface.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Domain model for a category of buyable items
 */
@DatabaseTable(tableName = "categories")
public class Category {

    public Category() {}

    public Category(Shop shop, String name) {
        this.shop = shop;
        this.name = name;
    }

    @DatabaseField(generatedId = true, columnName = "record_id")
    private int recordId; // primary key, not null

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    private Shop shop; // must be refreshed before accessing anything other than ID
    // 'foreignAutoRefresh = true' can be set if needed

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @ForeignCollectionField(foreignFieldName = "category")
    public ForeignCollection<BuyableItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop getShop() {
        return shop;
    }
}
