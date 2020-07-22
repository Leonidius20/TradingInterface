package ua.leonidius.trdinterface.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

public class DeleteItemController extends ModalController {

    private final ShopItem item;

    public DeleteItemController(ScreenManager manager, ShopItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ModalScreen(Message.WDW_DELETE_ITEM_TITLE.getText(),
                item instanceof BuyableItem ?
                        Message.WDW_DELETE_BUY_ITEM_CONF.getText(item.getName())
                        : Message.WDW_DELETE_SELL_ITEM_CONF.getText(item.getName()),
                        this), true);
    }


    @Override
    public void onPositiveResponse() {
        // Saving data for logging
        String itemName = item.getName();
        String itemId = item.getItemId();

        try {
            if (item instanceof BuyableItem) {
                Dao<BuyableItem, Integer> itemDao =
                        DaoManager.createDao(Trading.getSource(), BuyableItem.class);
                itemDao.delete((BuyableItem) item);
            } else {
                Dao<SellableItem, Integer> itemDao =
                        DaoManager.createDao(Trading.getSource(), SellableItem.class);
                itemDao.delete((SellableItem) item);
            }
        } catch (SQLException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
            new InfoController(manager, Message.ERROR.getText()).showScreen();
            return;
        }

        if (Trading.settings.editLogging) {
            if (item instanceof BuyableItem) {
                Message.LOG_BUY_ITEM_DELETED.log(manager.getPlayer().getName(),
                        itemName, itemId);
            } else Message.LOG_SELL_ITEM_DELETED.log(manager.getPlayer().getName(),
                    itemName, itemId);

        }

        if (item instanceof BuyableItem) manager.backTwoScreens();
        else manager.back();
    }

}
