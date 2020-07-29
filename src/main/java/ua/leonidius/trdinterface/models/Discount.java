package ua.leonidius.trdinterface.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "discounts")
public class Discount {

    public Discount() {
    }

    public Discount(BuyableItem item, double percent, long endTime) {
        this.item = item;
        this.percent = percent;
        this.endTime = endTime;
    }

    @DatabaseField(generatedId = true, columnName = "record_id")
    private int recordId;

    @DatabaseField(canBeNull = false, foreign = true, unique = true)
    private BuyableItem item;

    @DatabaseField(canBeNull = false)
    private double percent;

    @DatabaseField(canBeNull = false, columnName = "end_time")
    private long endTime; // seconds since 01/01/1970

    public double getPercent() {
        return percent;
    }

}
