package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.edit.categories.DeleteCategoryScreen;
import ua.leonidius.trdinterface.buy.edit.categories.RenameCategoryScreen;
import ua.leonidius.trdinterface.buy.edit.items.AddBuyItemScreen;
import ua.leonidius.trdinterface.elements.ItemButton;
import ua.leonidius.trdinterface.screens.InfoScreen;
import ua.leonidius.trdinterface.screens.SimpleScreen;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyItemSelectorScreen extends SimpleScreen {

    private transient int shopId, categoryId;
    private transient String categoryName;

    public BuyItemSelectorScreen(ScreenManager manager, int shopId, int categoryId) throws SQLException, IOException {
        super(manager); // TODO: title

        this.shopId = shopId;
        this.categoryId = categoryId;

        update();
    }

    @Override
    public void update() throws SQLException, IOException {
        getButtons().clear();
        setContent("");

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText())); // 0

        boolean hasPermission = getScreenManager().getPlayer().hasPermission("shop.edit");
        if (hasPermission) {
            addButton(new ElementButton(Message.BTN_RENAME_CATEGORY.getText())); // 1
            addButton(new ElementButton(Message.BTN_DELETE_CATEGORY.getText())); // 2
            addButton(new ElementButton(Message.BTN_ADD_ITEM.getText())); // 3
        }

        categoryName = ShopHelper.getBuyCategoryName(categoryId);

        String query = "SELECT * FROM buy_items WHERE category_id = ?";
        PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
        statement.setInt(1, categoryId);
        ResultSet items = statement.executeQuery();
        while (items.next()) {
            int recordId = items.getInt("record_id");
            String itemId = items.getString("id");
            Item item = Item.fromString(itemId);
            byte[] nbtBytes = items.getBytes("nbt");
            if (nbtBytes != null && nbtBytes.length != 0) {
                CompoundTag nbt = NBTIO.read(nbtBytes);
                item.setCompoundTag(nbt);
            }
            double price = items.getDouble("price");
            addButton(new ItemButton(ShopHelper.buildItemButtonText(item, price), recordId));
        }

        // If there is only back button
        if ((!hasPermission && getButtons().size() == 1) || (hasPermission && getButtons().size() == 4)) {
            setContent(Message.WDW_BUY_EMPTY_CAT.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        boolean hasPermission = getScreenManager().getPlayer().hasPermission("shop.edit");

        if (getResponse().getClickedButtonId() == 0) { // Back button
            getScreenManager().back();
            return;
        }

        if (hasPermission) {
            switch (getResponse().getClickedButtonId()) {
                case 1: // Rename category button
                    getScreenManager().addAndShow(new RenameCategoryScreen(getScreenManager(),
                            categoryId, categoryName), true);
                    return;
                case 2: // Delete category button
                    setIgnoreStack(true); // When the category is deleted, we want to return to BuyCategoriesScreen
                    getScreenManager().addAndShow(new DeleteCategoryScreen(getScreenManager(),
                            categoryName, categoryId), true);
                    return;
                case 3: // Add item button
                    getScreenManager().addAndShow(new AddBuyItemScreen(getScreenManager(),
                            shopId, categoryId), true);
                    return;
            }
        }

        int itemRecordId = ((ItemButton)getResponse().getClickedButton()).getItemId();

        if (hasPermission) {
            try {
                getScreenManager().addAndShow(new BuyManageItemScreen(getScreenManager(), itemRecordId));
            } catch (SQLException | IOException e) {
                if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
            }
            return;
        }

        try {
            Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemRecordId);
            Item item = itemAndPrice.keySet().iterator().next();
            double priceWithoutDiscount = itemAndPrice.get(item);

            // Checking if there is enough place in inventory and money
            double price = priceWithoutDiscount; // TODO: account for discount
            int maxByMoney = Buy.getMaxByMoney(player, price);
            int maxByInventory = Buy.getMaxByInventory(player, item);

            if (maxByMoney == 0 && maxByInventory == 0) {
                String message = Message.BUY_NO_SPACE_AND_MONEY.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else if (maxByMoney == 0) {
                String message = Message.BUY_NO_MONEY.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else if (maxByInventory == 0) {
                String message = Message.BUY_NO_SPACE.getText();
                getScreenManager().addAndShow(new InfoScreen(getScreenManager(), message));
            } else {
                int maxAmount = Math.min(maxByInventory, maxByMoney);
                BuySelectAmountScreen screen = new BuySelectAmountScreen(getScreenManager(), itemRecordId, maxAmount);
                getScreenManager().addAndShow(screen, true);
            }
        } catch (SQLException | IOException e) {
            if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
            getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
        }
    }

}