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

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId; // primary key, not null

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop; // must be refreshed before accessing anything other than ID
    // 'foreignAutoRefresh = true' can be set if needed

    @DatabaseField(canBeNull = false, unique = true)
    String name;

    @ForeignCollectionField(columnName = "items")
    ForeignCollection<BuyableItem> items;

}
