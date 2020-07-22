package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;

public abstract class ShopItem {

    protected abstract int getRecordId();

    public abstract String getItemId();

    public abstract double getPrice();

    public abstract byte[] getNbt();

    public abstract void setItemId(String itemId);

    public abstract void setPrice(double price);

    public abstract void setNbt(byte[] nbt);

    /**
     * Used to cache game item for optimization
     */
    private Item gameItem = null;

    public Item toGameItem() {
        if (gameItem != null) return gameItem;

        Item item = Item.fromString(getItemId());
        if (getNbt() != null) {
            try {
                CompoundTag tag = NBTIO.read(getNbt());
                item.setCompoundTag(tag);
            } catch (IOException e) {
                // TODO: translate the error message
                Trading.getPlugin().getLogger().error(
                        "Error reading NBT Tag on item with record_id "
                                + getRecordId());
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

    public String getName() {
        return toGameItem().getName();
    }

}
