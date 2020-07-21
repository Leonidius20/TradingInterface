package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.controllers.NamingController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.NamingScreen;

import java.sql.SQLException;

public class RenameCategoryController extends NamingController {

    private final Category category;

    public RenameCategoryController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new NamingScreen(this,
                Message.WDW_RENAME_CATEGORY_TITLE.getText(),
                Message.WDW_RENAME_CATEGORY_NAME.getText(),
                category.name));
    }

    @Override
    public void giveName(String name) {
        try {
            String oldName = category.name;
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            category.name = name;
            categoryDao.update(category);

            if (Trading.settings.editLogging) {
                Message.LOG_CATEGORY_RENAMED.log(manager.getPlayer().getName(), oldName, name);
            }

            manager.back();
        } catch (SQLException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
            new InfoController(manager, "", Message.ERROR.getText());
        }
    }

}
