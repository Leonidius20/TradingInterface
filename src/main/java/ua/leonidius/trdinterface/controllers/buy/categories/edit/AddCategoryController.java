package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.sqlite.SQLiteErrorCode;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.controllers.NamingController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;

import java.sql.SQLException;

public class AddCategoryController extends NamingController {

    private final Shop shop;

    public AddCategoryController(ScreenManager manager, Shop shop) {
        super(manager);
        this.shop = shop;
    }

    @Override
    protected String getScreenTitle() {
        return Message.WDW_NEW_CATEGORY.getText();
    }

    @Override
    protected String getInputFieldHint() {
        return Message.WDW_NEW_CATEGORY_NAME.getText();
    }

    @Override
    public void submitName(String name) {
        try {
            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            Category newCategory = new Category(shop, name);
            categoryDao.create(newCategory);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_CATEGORY_ADDED.log(manager.getPlayer().getName(), name);
            }

            manager.back();
        } catch (SQLException e) {
            Trading.printException(e);

            InfoController controller;
            // TODO: test this
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                controller = new InfoController(manager, Message.WDW_NEW_CATEGORY_FAIL.getText());
            } else {
                controller = new InfoController(manager, Message.ERROR.getText());
            }
            controller.showScreen();
        }
    }

}
