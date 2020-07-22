package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;

@DatabaseTable(tableName = "buyable_items")
public class BuyableItem extends ShopItem {

    public BuyableItem() {}

    @DatabaseField(generatedId = true, columnName = "record_id")
    private int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "category_id")
    Category category;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    private String itemId;

    @DatabaseField(canBeNull = false)
    private double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] nbt;

    public static BuyableItem fromGameItem(Category category, Item gameItem, double price) {
        BuyableItem item = new BuyableItem();
        item.shop = category.shop;
        item.category = category;
        item.price = price;
        item.itemId = gameItem.getId() + ":" + gameItem.getDamage();
        try {
            // TODO: maybe replace with getCompoundTag() with returns byte[]
            item.nbt = gameItem.getNamedTag() == null ? null : NBTIO.write(gameItem.getNamedTag());
        } catch (IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
        }
        return item;
    }

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
        return price;
    }

    @Override
    public byte[] getNbt() {
        return nbt;
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

}
