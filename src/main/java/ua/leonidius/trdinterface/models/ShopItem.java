package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class ShopItem {

    protected abstract int getRecordId();

    public abstract String getItemId();

    /**
     * @return price with discount (if there is a discount)
     */
    public abstract double getPrice();

    /**
     * @return price without discount
     */
    public abstract double getPurePrice();

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
                Message.LOG_READING_NBT_FAILED.error(getRecordId());
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

        if (itemId.isEmpty()) {
            throw new IllegalArgumentException(
                    Message.WDW_ADD_ITEM_MISSING_ID.getText());
        }

        if ((gameItem = Item.fromString(itemId)).getId() == 0) {
            throw new IllegalArgumentException(
                    Message.WDW_ADD_ITEM_INVALID_ID.getText());
        }

        double price;
        try {
            price = Double.parseDouble(priceS.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    Message.WDW_ADD_ITEM_INVALID_PRICE.getText());
        }
        if (price < 0) {
            throw new IllegalArgumentException(
                    Message.WDW_EDIT_ITEM_NEGATIVE_PRICE.getText());
        }

        if (customName != null && !customName.isEmpty())
            gameItem.setCustomName(customName);

        if (customLore != null && !customLore.isEmpty())
            gameItem.setLore(customLore.split("\n"));

        if (enchantments != null && enchantments.length != 0)
            gameItem.addEnchantment(enchantments);

        item.setItemId(itemId);
        item.setPrice(price);

        try {
            item.setNbt(gameItem.getNamedTag() == null ? null
                    : NBTIO.write(gameItem.getNamedTag()));
        } catch (IOException e) {
            Trading.printException(e);
            throw new IllegalArgumentException(
                    Message.WDW_ADD_ITEM_INVALID_NBT.getText());
        }
    }

    /**
     * Used to cache the item's original name
     * (= not a custom name, like the one added
     * with an anvil) to avoid querying for custom
     * translation more than once
     */
    private String name = null;

    /**
     * Returns the name of the item, which my be a custom name
     * or a custom translation
     *
     * @return name of the item
     */
    public String getName() {
        if (toGameItem().hasCustomName())
            return toGameItem().getCustomName();
        return getOriginalName();
    }

    /**
     * Returns the name of an item, which may have a custom translation
     *
     * @return original name
     */
    public String getOriginalName() {
        if (name == null) {
            try {
                Dao<Translation, Integer> translationDao =
                        DaoManager.createDao(Trading.getSource(), Translation.class);

                List<Translation> list =
                        translationDao.queryForEq("item_id", getItemId());

                if (list.size() != 0) {
                    name = list.get(0).getTranslation();
                } else name = Item.fromString(getItemId()).getName();
            } catch (SQLException e) {
                Trading.printException(e);
                name = Item.fromString(getItemId()).getName();
            }
        }
        return name;
    }

    public abstract void update() throws SQLException;

}
