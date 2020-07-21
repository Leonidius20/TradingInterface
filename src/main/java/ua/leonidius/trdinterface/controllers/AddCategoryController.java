package ua.leonidius.trdinterface.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.sqlite.SQLiteErrorCode;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.InfoScreen;
import ua.leonidius.trdinterface.views.screens.NamingScreen;

import java.sql.SQLException;

public class AddCategoryController extends NamingController {

    private final Shop shop;

    AddCategoryController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new NamingScreen(this,
                Message.WDW_NEW_CATEGORY.getText(),
                Message.WDW_NEW_CATEGORY_NAME.getText()), true);
    }

    @Override
    public void giveName(String name) {
        try {
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            Category newCategory = new Category(shop, name);
            categoryDao.create(newCategory);

            if (Trading.settings.editLogging) {
                Message.LOG_CATEGORY_ADDED.log(manager.getPlayer().getName(), name);
            }

            // TODO: replace with InfoController
            manager.addAndShow(new InfoScreen(manager, Message.WDW_NEW_CATEGORY_SUCCESS.getText()));

        } catch (SQLException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
                Trading.getPlugin().getLogger().error("Error code: " + e.getErrorCode());
            }

            // TODO: replace with InfoController
            InfoScreen screen;
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                screen = new InfoScreen(manager, Message.WDW_NEW_CATEGORY_FAIL.getText());
            } else {
                screen = new InfoScreen(manager, Message.ERROR.getText());
            }
            manager.addAndShow(screen);
        }
    }

}
