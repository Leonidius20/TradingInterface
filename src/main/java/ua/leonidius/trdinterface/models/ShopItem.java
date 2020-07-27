package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;
import java.sql.SQLException;

public abstract class ShopItem {

    protected abstract int getRecordId();

    public abstract String getItemId();

    public abstract double getPrice();

    public abstract byte[] getNbt();

    public abstract void setItemId(String itemId);

    public abstract void setPrice(double price);

    public abstract void setNbt(byte[] nbt);

    public abstract void setShop(Shop shop);

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

    /**
     * Used to populate the model with item_id, price and nbt
     * This method doesn't change shop and category (they
     * have to be populated separately)
     * @param item item to populate
     * @param <T> BuyableItem or SellableItem
     */
    public static <T extends ShopItem> void populate(T item, String itemId, String priceS,
                                                     String customName, String customLore)
            throws IllegalArgumentException {
        Item gameItem;

        if (itemId.isEmpty() || (gameItem = Item.fromString(itemId)).getId() == 0) {
            throw new IllegalArgumentException("Please fill in all of the required parameters");
        }

        double price;
        try {
            price = Double.parseDouble(priceS.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Incorrect price"); // TODO: translate and show
        }

        if (customName != null && !customName.isEmpty())
            gameItem.setCustomName(customName);

        if (customLore != null && !customLore.isEmpty())
            gameItem.setLore(customLore.split("\n"));

        item.setItemId(itemId);
        item.setPrice(price);

        try {
            item.setNbt(gameItem.getNamedTag() == null ? null : NBTIO.write(gameItem.getNamedTag()));
        } catch (IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
                throw new IllegalArgumentException("Incorrect NBT");
            }
        }
    }

    public String getName() {
        // TODO: custom names
        return toGameItem().getName();
    }

    public abstract void update() throws SQLException;

}
