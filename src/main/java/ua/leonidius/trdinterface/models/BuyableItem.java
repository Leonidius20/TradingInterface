package ua.leonidius.trdinterface.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "buyable_items")
public class BuyableItem {

    public BuyableItem() {}

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "category_id")
    Category category;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    String itemId;

    @DatabaseField(canBeNull = false)
    double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] nbt;

}
