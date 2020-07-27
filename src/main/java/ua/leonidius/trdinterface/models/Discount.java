package ua.leonidius.trdinterface.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "discounts")
public class Discount {

    @DatabaseField(canBeNull = false, foreign = true, unique = true)
    BuyableItem item;

    @DatabaseField(canBeNull = false)
    double percent;

    @DatabaseField(canBeNull = false, columnName = "creation_time")
    int creationTime; // ms since 01/01/1970

    @DatabaseField(canBeNull = false, columnName = "end_time")
    int endTime;

}
