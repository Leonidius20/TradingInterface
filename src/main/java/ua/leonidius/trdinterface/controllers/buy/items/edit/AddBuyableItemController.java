package ua.leonidius.trdinterface.controllers.buy.items.edit;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.edit.AddBuyableItemScreen;

import java.sql.SQLException;

import static ua.leonidius.trdinterface.Trading.settings;

public class AddBuyableItemController extends BaseController {

    private final Category category;

    public AddBuyableItemController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AddBuyableItemScreen(this), true);
    }

    public void addItem(String itemId, String priceS, String customName, String customLore) {
        Item item;

        if (itemId.isEmpty() || (item = Item.fromString(itemId)).getId() == 0) {
            showErrorScreen();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceS.replace(",", "."));
        } catch (NumberFormatException e) {
            showErrorScreen();
            return;
        }

        if (customName != null && !customName.isEmpty())
            item.setCustomName(customName);

        if (customLore != null && !customLore.isEmpty())
            item.setLore(customLore.split("\n"));

        BuyableItem buyableItem = BuyableItem.fromGameItem(category, item, price);

        try {
            Dao<BuyableItem, Integer> itemDao =
                    DaoManager.createDao(Trading.getSource(), BuyableItem.class);
            itemDao.create(buyableItem);
        } catch (SQLException e) {
            if (Trading.settings.debugMode)
                Trading.getPlugin().getLogger().error(e.getMessage());
            showErrorScreen();
            return;
        }

        if (Trading.settings.editLogging) {
            if (customName == null || customName.equals("")) {
                Message.LOG_BUY_ITEM_ADDED.log(manager.getPlayer().getName(), item.getName(),
                        item.getId() + ":" + item.getDamage(), price, settings.currency);
            } else {
                Message.LOG_BUY_ITEM_ADDED_WITH_CUSTOM_NAME.log(manager.getPlayer().getName(), customName,
                        Item.fromString(item.getId() + ":" + item.getDamage()).getName(),
                        item.getId() + ":" + item.getDamage(), price, settings.currency);
            }
        }

        String message = Message.WDW_ADD_BUY_ITEM_SUCCESS.getText(item.getName());
        new InfoController(manager, message).showScreen();
    }

    private void showErrorScreen() {
        new InfoController(manager, Message.ERROR.getText()).showScreen();
    }

}
