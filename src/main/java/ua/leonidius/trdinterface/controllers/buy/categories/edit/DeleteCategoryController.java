package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import cn.nukkit.utils.TextFormat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ModalController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

public class DeleteCategoryController extends ModalController {

    private final Category category;

    public DeleteCategoryController(ScreenManager manager, Category category) {
        super(manager);
        this.category = category;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ModalScreen(Message.WDW_DELETE_CATEGORY_TITLE.getText(),
                Message.WDW_DELETE_CATEGORY_CONF.getText(
                        TextFormat.colorize(TextFormat.BOLD.getChar(), category.name)),
                        this), true);
    }

    @Override
    public void onPositiveResponse() {
        try {
            category.items.clear();

            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            categoryDao.delete(category);

            manager.backTwoScreens();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
