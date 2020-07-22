package ua.leonidius.trdinterface.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.buy.categories.CategoriesController;
import ua.leonidius.trdinterface.controllers.sell.SellableItemsController;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.MainScreen;

import java.sql.SQLException;

public class MainController extends BaseController {

    private Shop shop;

    public MainController(ScreenManager manager, int shopId) {
        super(manager);
        try {
            Dao<Shop, Integer> shopDao = DaoManager.createDao(Trading.getSource(), Shop.class);
            this.shop = shopDao.queryForId(shopId);
        } catch (SQLException e) {
            // TODO: show an error screen
            Trading.getPlugin().getLogger().debug(e.getMessage());
            if (Trading.settings.debugMode) e.printStackTrace();
        }
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new MainScreen(this));
    }

    public void buy() {
        new CategoriesController(manager, shop).showScreen();
    }

    public void sell() {
        new SellableItemsController(manager, shop).showScreen();
    }

}
