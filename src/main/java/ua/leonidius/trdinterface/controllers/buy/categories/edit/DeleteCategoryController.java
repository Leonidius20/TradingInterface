package ua.leonidius.trdinterface.controllers.buy.categories.edit;

import cn.nukkit.utils.TextFormat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

public class DeleteCategoryController extends BaseController {

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
                this::deleteCategory, manager::back));
    }

    private void deleteCategory() {
        try {
            category.items.clear();

            Dao<Category, Integer> categoryDao =
                    DaoManager.createDao(Trading.getSource(), Category.class);
            categoryDao.delete(category);

            manager.backTwoScreens();
        } catch (SQLException e) {
            if (Trading.settings.debugMode) {
                Trading.getPlugin().getLogger().error(e.getMessage());
            }
            new InfoController(manager, Message.ERROR.toString()).showScreen();
        }
    }

}
