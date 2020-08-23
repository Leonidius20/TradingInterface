package ua.leonidius.trdinterface.controllers.buy.items.edit;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.controllers.ItemDetailsEditController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ItemDetailsEditScreen;

import java.sql.SQLException;

public class AddBuyableItemController extends ItemDetailsEditController {

    private final Category category;

    public AddBuyableItemController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ItemDetailsEditScreen(this,
                Message.WDW_ADD_ITEM_TITLE.getText()), true);
    }

    @Override
    public void submitDetails(String itemId, String priceS, String customName, String customLore) {
        BuyableItem item = new BuyableItem();
        item.setShop(category.getShop());
        item.setCategory(category);

        try {
            BuyableItem.populate(item, itemId, priceS, customName, customLore);
        } catch (IllegalArgumentException e) {
            // we don't want this to be printed to console,
            // that's why we don't use handleException()
            showErrorScreen(e.getMessage());
            return;
        }

        try {
            Dao<BuyableItem, Integer> itemDao =
                    DaoManager.createDao(Trading.getSource(), BuyableItem.class);
            itemDao.create(item);
            itemDao.assignEmptyForeignCollection(item, "discounts");
        } catch (SQLException e) {
            handleException(e);
            return;
        }

        if (Trading.getSettings().logEdits()) {
            if (customName == null || customName.equals("")) {
                Message.LOG_BUY_ITEM_ADDED.log(manager.getPlayer().getName(), item.getName(),
                        item.getItemId(), item.getPrice(),
                        Trading.getSettings().getCurrency());
            } else {
                Message.LOG_BUY_ITEM_ADDED_WITH_CUSTOM_NAME.log(manager.getPlayer().getName(), customName,
                        Item.fromString(item.getItemId()).getName(),
                        item.getItemId(), item.getPrice(),
                        Trading.getSettings().getCurrency());
            }
        }

        new InfoController(manager, Message.WDW_SUCCESS_TITLE.getText(),
                Message.WDW_ADD_BUY_ITEM_SUCCESS.getText(item.getName()))
                .showScreen();
    }

}
