package ua.leonidius.trdinterface.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sellable_items")
public class SellableItem {

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    String itemId;

    @DatabaseField(canBeNull = false)
    double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] nbt;

}
