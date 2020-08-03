package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
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
                        "Error reading NBT tag on item with record_id "
                                + getRecordId());
                Trading.printException(e);
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

    public static <T extends ShopItem> void populate(T item, String itemId, String priceS,
                                                     String customName, String customLore) {
        populate(item, itemId, priceS, customName, customLore, new Enchantment[0]);
    }

    /**
     * Used to populate the model with item_id, price and nbt
     * This method doesn't change shop and category (they
     * have to be populated separately)
     *
     * @param item item to populate
     * @param <T>  BuyableItem or SellableItem
     */
    public static <T extends ShopItem> void populate(T item, String itemId, String priceS,
                                                     String customName, String customLore,
                                                     Enchantment[] enchantments)
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

        gameItem.addEnchantment(enchantments);

        item.setItemId(itemId);
        item.setPrice(price);

        try {
            item.setNbt(gameItem.getNamedTag() == null ? null : NBTIO.write(gameItem.getNamedTag()));
        } catch (IOException e) {
            Trading.printException(e);
            throw new IllegalArgumentException("Incorrect NBT");
        }
    }

    /**
     * Used to cache the item's name to avoid querying for custom
     * translation more than once
     */
    private String name = null;

    /**
     * Returns the name of the item, which may have a custom translation
     *
     * @return name of the item
     */
    public String getName() {
        if (name == null) {
            if (toGameItem().hasCustomName())
                return name = toGameItem().getCustomName();

            try {
                Dao<Translation, Integer> translationDao =
                        DaoManager.createDao(Trading.getSource(), Translation.class);
                translationDao.queryForEq("itemId", getItemId());

                if (translationDao.iterator().hasNext()) {
                    return name = translationDao.iterator().next().getTranslation();
                }
                translationDao.iterator().closeQuietly();
            } catch (SQLException e) {
                Trading.printException(e);
            }

            return name = toGameItem().getName();
        }

        return name;
    }

    /**
     * Deleting the cached version of the item's name in order
     * for the new name to be shown after a custom translation was added
     */
    public void resetName() {
        name = null;
    }

    public abstract void update() throws SQLException;

}
