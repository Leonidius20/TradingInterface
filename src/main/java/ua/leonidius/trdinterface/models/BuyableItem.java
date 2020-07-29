package ua.leonidius.trdinterface.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;
import java.sql.SQLException;

@DatabaseTable(tableName = "buyable_items")
public class BuyableItem extends ShopItem {

    public BuyableItem() {}

    @DatabaseField(generatedId = true, columnName = "record_id")
    private int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    private Shop shop;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "category_id")
    private Category category;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    private String itemId;

    @DatabaseField(canBeNull = false)
    private double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] nbt;

    @ForeignCollectionField(columnName = "discounts", foreignFieldName = "item",
            eager = true)
    private ForeignCollection<Discount> discount;

    @Override
    protected int getRecordId() {
        return recordId;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public double getPrice() {
        if (getDiscount() == null) return price;
        return price - (price * (getDiscount().getPercent() / 100));
    }

    public double getOriginalPrice() {
        return price;
    }

    @Override
    public byte[] getNbt() {
        return nbt;
    }

    /**
     * @return discount for the item if there is one, null otherwise
     */
    public Discount getDiscount() {
        if (discount.size() == 0) return null;
        Discount result = discount.iterator().next();
        try {
            discount.closeLastIterator();
        } catch (IOException e) {
            Trading.printException(e);
        }
        return result;
    }

    public void removeDiscount() {
        Discount d = getDiscount();
        if (d == null) return;
        discount.remove(d);
    }

    public void addDiscount(Discount d) {
        discount.add(d);
    }

    @Override
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setNbt(byte[] nbt) {
        this.nbt = nbt;
    }

    @Override
    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public void update() throws SQLException {
        Dao<BuyableItem, Integer> itemDao =
                DaoManager.createDao(Trading.getSource(), BuyableItem.class);
        itemDao.update(this);
    }

}
