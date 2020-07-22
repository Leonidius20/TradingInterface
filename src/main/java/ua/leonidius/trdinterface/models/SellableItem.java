package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;

@DatabaseTable(tableName = "sellable_items")
public class SellableItem implements ShopItem {

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

    /**
     * Used to cache game item for optimization
     */
    private Item gameItem = null;

    // TODO: this is a copy-paste from BuyableItem. Find a way to avoid that
    public Item toGameItem() {
        if (gameItem != null) return gameItem;

        Item item = Item.fromString(itemId);
        if (nbt != null) {
            try {
                CompoundTag tag = NBTIO.read(nbt);
                item.setCompoundTag(tag);
            } catch (IOException e) {
                // TODO: translate the error message
                Trading.getPlugin().getLogger().error("Error reading NBT Tag on item with record_id " + recordId);
                if (Trading.settings.debugMode) {
                    Trading.getPlugin().getLogger().error(e.getMessage());
                }
            }
        }

        return gameItem = item;
    }

    /**
     * Deleting the cached version of game item
     * Used when after a change in an item we need
     * to update its description in ManageBuyableItemScreen
     */
    public void resetGameItem() {
        gameItem = null;
    }

    @Override
    public String getName() {
        return toGameItem().getName();
    }

    @Override
    public String getItemId() {
        return itemId;
    }

}
