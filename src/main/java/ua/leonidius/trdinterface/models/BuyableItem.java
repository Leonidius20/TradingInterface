package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;

@DatabaseTable(tableName = "buyable_items")
public class BuyableItem implements ShopItem {

    public BuyableItem() {}

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "category_id")
    Category category;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    public String itemId;

    @DatabaseField(canBeNull = false)
    public double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    public byte[] nbt;

    /**
     * Used to cache game item for optimization
     */
    private Item gameItem = null;

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

    public static BuyableItem fromGameItem(Category category, Item gameItem, double price) {
        BuyableItem item = new BuyableItem();
        item.shop = category.shop;
        item.category = category;
        item.price = price;
        item.itemId = gameItem.getId() + ":" + gameItem.getDamage();
        try {
            item.nbt = gameItem.getNamedTag() == null ? null : NBTIO.write(gameItem.getNamedTag());
        } catch (IOException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
        }
        return item;
    }

    public String buildDescription() {
        StringBuilder sb = new StringBuilder();

        Item gameItem = toGameItem();

        // TODO: apply discount to price

        sb.append(Message.WDW_BUY_NAME.getText(gameItem.getName()));
        sb.append("\n");

        Item pureItem = Item.fromString(gameItem.getId() + ":" + gameItem.getDamage());
        sb.append(Message.WDW_BUY_ORIGINAL_NAME.getText(pureItem.getName(),
                gameItem.getId(), gameItem.getDamage()));

        sb.append("\n").append(Message.WDW_BUY_PRICE.getText(price,
                Trading.settings.currency));

        /*if (shopItem.discount != 0) {
            sb.append("\n")
                    .append(Message.WDW_BUY_DISCOUNT.getText(shopItem.discount, shopItem.calculatePrice(), settings.currency));
        }*/

        if (gameItem.getLore() != null && gameItem.getLore().length != 0) {
            StringBuilder loreBuilder = new StringBuilder();
            for (String line : gameItem.getLore()) {
                loreBuilder.append(line).append("\n");
            }
            sb.append("\n").append(Message.WDW_BUY_CUSTOM_LORE.getText(loreBuilder.toString()));
        }

        Enchantment[] enchantments = gameItem.getEnchantments();
        if (enchantments != null && enchantments.length != 0) {
            sb.append("\n");
            StringBuilder eListBuilder = new StringBuilder();
            for (int i = 0; i < enchantments.length; i++) {
                Enchantment enchantment = enchantments[i];
                eListBuilder.append(enchantment.getName()).append(" ").append(enchantment.getLevel());
                if (i != enchantments.length - 1) eListBuilder.append(", ");
            }
            sb.append(Message.WDW_BUY_ENCHANTMENTS.getText(eListBuilder.toString()));
        }

        return sb.toString();
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
