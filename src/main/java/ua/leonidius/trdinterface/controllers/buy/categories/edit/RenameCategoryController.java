package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.NamingController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;

import java.sql.SQLException;

public class RenameCategoryController extends NamingController {

    private final Category category;

    public RenameCategoryController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    protected String getScreenTitle() {
        return Message.WDW_RENAME_CATEGORY_TITLE.getText();
    }

    @Override
    protected String getInputFieldHint() {
        return Message.WDW_RENAME_CATEGORY_NAME.getText();
    }

    @Override
    public void submitName(String name) {
        try {
            String oldName = category.name;
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            category.name = name;
            categoryDao.update(category);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_CATEGORY_RENAMED.log(manager.getPlayer().getName(), oldName, name);
            }

            manager.back();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
