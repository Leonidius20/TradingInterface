package ua.leonidius.trdinterface.controllers.sell.edit;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.controllers.ItemDetailsEditController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemDetailsEditScreen;

import java.sql.SQLException;

public class AddSellableItemController extends ItemDetailsEditController {

    private final Shop shop;

    public AddSellableItemController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ItemDetailsEditScreen(this,
                Message.WDW_ADD_ITEM_TITLE.getText()), true);
    }

    @Override
    public void submitDetails(String itemId, String priceS, String customName, String customLore) {
        SellableItem item = new SellableItem();
        item.setShop(shop);

        try {
            SellableItem.populate(item, itemId, priceS, customName, customLore);
        } catch (IllegalArgumentException e) {
            showErrorScreen(e.getMessage());
        }

        try {
            Dao<SellableItem, Integer> itemDao =
                    DaoManager.createDao(Trading.getSource(), SellableItem.class);
            itemDao.create(item);
        } catch (SQLException e) {
            if (Trading.settings.debugMode)
                Trading.getPlugin().getLogger().error(e.getMessage());
            showErrorScreen();
            return;
        }

        if (Trading.settings.editLogging) {
            if (customName == null || customName.equals("")) {
                Message.LOG_SELL_ITEM_ADDED.log(manager.getPlayer().getName(),
                        item.getName(), item.getItemId(),
                        item.getPrice(), Trading.settings.currency);
            } else {
                Message.LOG_SELL_ITEM_ADDED_WITH_CUSTOM_NAME.log(
                        manager.getPlayer().getName(), customName,
                        Item.fromString(item.getItemId()).getName(),
                        item.getItemId(), item.getPrice(),
                        Trading.settings.currency);
            }
        }

        new InfoController(manager, Message.WDW_SUCCESS_TITLE.getText(),
                Message.WDW_ADD_SELL_ITEM_SUCCESS.getText(item.getName()))
                .showScreen();
    }

}
