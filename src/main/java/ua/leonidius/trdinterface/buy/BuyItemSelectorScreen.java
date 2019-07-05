package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import ua.leonidius.trdinterface.*;
import ua.leonidius.trdinterface.buy.edit.items.AddBuyItemScreen;
import ua.leonidius.trdinterface.elements.ItemButton;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyItemSelectorScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId;
    private boolean hasPermission;
    private String categoryName;

    public BuyItemSelectorScreen(int shopId, int categoryId, boolean hasPermission) {
        super("", "");

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.hasPermission = hasPermission;

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText())); // 0

        if (hasPermission) {
            addButton(new ElementButton(Message.BTN_RENAME_CATEGORY.getText())); // 1
            addButton(new ElementButton(Message.BTN_DELETE_CATEGORY.getText())); // 2
            addButton(new ElementButton(Message.BTN_ADD_ITEM.getText())); // 3
        }

        try {
            String nameQuery = "SELECT name FROM categories WHERE record_id = ?";
            PreparedStatement nameStatement = Trading.getDbConnection().prepareStatement(nameQuery);
            nameStatement.setInt(1, categoryId);
            ResultSet nameResults = nameStatement.executeQuery();
            nameResults.next();
            categoryName = nameResults.getString("name");


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
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
        }

        // If there is only back button
        if ((!hasPermission && getButtons().size() == 1) || (hasPermission && getButtons().size() == 4)) {
            setContent(Message.WDW_BUY_EMPTY_CAT.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (getResponse().getClickedButtonId() == 0) { // Back button
            player.showFormWindow(new BuyCategoriesScreen(shopId, hasPermission));
            return;
        }

        if (hasPermission) {
            switch (getResponse().getClickedButtonId()) {
                case 1: // Rename category button
                    player.showFormWindow(new RenameCategoryScreen(shopId, categoryId, categoryName));
                    return;
                case 2: // Delete category button
                    player.showFormWindow(new DeleteCategoryScreen(categoryName, shopId, categoryId));
                    return;
                case 3: // Add item button
                    player.showFormWindow(new AddBuyItemScreen(shopId, categoryId));
                    return;
            }
        }

        int itemRecordId = ((ItemButton) getResponse().getClickedButton()).getItemId();
        if (hasPermission) {
            player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemRecordId));
            return;
        }

        try {
            String query = "SELECT price, id, nbt FROM buy_items WHERE record_id = ?";
            PreparedStatement statement = Trading.getDbConnection().prepareStatement(query);
            statement.setInt(1, itemRecordId);
            ResultSet results = statement.executeQuery();
            results.next();
            double priceWithoutDiscount = results.getDouble("price");
            Item item = Item.fromString(results.getString("id"));
            byte[] nbtBytes = results.getBytes("nbt");
            if (nbtBytes != null && nbtBytes.length != 0) {
                CompoundTag nbt = NBTIO.read(nbtBytes);
                item.setCompoundTag(nbt);
            }

            // Checking if there is enough place in inventory and money
            double price = priceWithoutDiscount; // TODO: account for discount
            int maxByMoney = Buy.getMaxByMoney(player, price);
            int maxByInventory = Buy.getMaxByInventory(player, item);

            if (maxByMoney == 0 && maxByInventory == 0) {
                player.showFormWindow(new BuyFailScreen(0, shopId, categoryId));
            } else if (maxByMoney == 0){
                player.showFormWindow(new BuyFailScreen(1, shopId, categoryId));
            } else if (maxByInventory == 0) {
                player.showFormWindow(new BuyFailScreen(2, shopId, categoryId));
            } else {
                int maxAmount = Math.min(maxByInventory, maxByMoney);
                player.showFormWindow(new BuySelectAmountScreen(player, shopId, categoryId, itemRecordId, maxAmount));
            }
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            player.showFormWindow(new BuyFailScreen(3, shopId, categoryId));
        }
    }

}