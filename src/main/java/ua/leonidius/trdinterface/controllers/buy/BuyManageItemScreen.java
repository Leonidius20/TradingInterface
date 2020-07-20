package ua.leonidius.trdinterface.controllers.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.buy.edit.items.DeleteItemScreen;
import ua.leonidius.trdinterface.controllers.buy.edit.items.enchantment.AddEnchantmentScreen;
import ua.leonidius.trdinterface.controllers.buy.edit.items.enchantment.RemoveEnchantmentScreen;
import ua.leonidius.trdinterface.views.screens.InfoScreen;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * This screen is shown to players who have permissions to manage the shop. It allows to select an action
 * to perform on an item: buy, edit, delete, edit discount,
 */
public class BuyManageItemScreen extends SimpleScreen {

    private transient int itemId;

    public BuyManageItemScreen(ScreenManager manager, int itemId) throws SQLException, IOException {
        super(Message.WDW_EDIT_ITEM_TITLE.getText());

        this.itemId = itemId;

        addButton(new ElementButton(Message.BTN_BACK.getText())); // 0
        addButton(new ElementButton(Message.BTN_BUY_ITEM.getText())); // 1
        addButton(new ElementButton(Message.BTN_EDIT_ITEM.getText())); // 2
        addButton(new ElementButton(Message.BTN_EDIT_DISCOUNT.getText())); // 3
        addButton(new ElementButton(Message.BTN_ADD_ENCHANTMENT.getText())); // 4
        addButton(new ElementButton(Message.BTN_REMOVE_ENCHANTMENT.getText())); // 5
        addButton(new ElementButton(Message.BTN_DELETE_ITEM.getText())); // 6

        update();
    }

    @Override
    public void update() throws SQLException, IOException {
        setContent(ShopHelper.buildDescription(itemId));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        /*Player player = event.getPlayer();
        switch (getResponse().getClickedButtonId()) {
            case 0: // Back
                getScreenManager().back();
                break;
            case 1: // Buy
                try {
                    Map<Item, Double> itemAndPrice = ShopHelper.getItemAndPrice(itemId);
                    Item item = itemAndPrice.keySet().iterator().next();

                    double priceWithoutDiscount = itemAndPrice.get(item);
                    double price = priceWithoutDiscount; // TODO: apply discount

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
                        getScreenManager().addAndShow(new BuySelectAmountScreen(getScreenManager(),
                                itemId, maxAmount), true);
                    }
                } catch (SQLException | IOException e) {
                    if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                    getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
                }
                break;
            case 2: // Edit
                break;
            case 3: // Edit discount
                break;
            case 4: // Add enchantment
                getScreenManager().addAndShow(new AddEnchantmentScreen(getScreenManager(), itemId), true);
                break;
            case 5: // Remove enchantment
                try {
                    getScreenManager().addAndShow(new RemoveEnchantmentScreen(getScreenManager(), itemId));
                } catch (SQLException | IOException e) {
                    if (Trading.settings.debugMode) Trading.getPlugin().getLogger().error(e.getMessage());
                    getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
                }
                break;
            case 6: // Delete item
                try {
                    Item item = ShopHelper.getItem(itemId);
                    DeleteItemScreen screen = new DeleteItemScreen(getScreenManager(), itemId,
                            item.getName(), item.getId() + ":" + item.getDamage());
                    getScreenManager().addAndShow(screen, true);
                } catch (SQLException | IOException e) {
                    getScreenManager().addAndShow(new InfoScreen(getScreenManager(), Message.ERROR.getText()));
                }
                break;
        }*/
    }

}