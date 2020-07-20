package ua.leonidius.trdinterface.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.MainScreen;

import java.sql.SQLException;

public class MainController {

    private Shop shop;
    private final ScreenManager manager;

    public MainController(ScreenManager manager, int shopId) {
        this.manager = manager;
        try {
            Dao<Shop, Integer> shopDao = DaoManager.createDao(Trading.getSource(), Shop.class);
            this.shop = shopDao.queryForId(shopId);
        } catch (SQLException e) {
            // TODO: show an error screen
            Trading.getPlugin().getLogger().debug(e.getMessage());
            if (Trading.settings.debugMode) e.printStackTrace();
        }
    }

    public void showScreen() {
        manager.addAndShow(new MainScreen(this));
    }

    public void buy() {
        new CategoriesController(manager, shop).showScreen();
    }

    public void sell() {
        // TODO
    }

}
