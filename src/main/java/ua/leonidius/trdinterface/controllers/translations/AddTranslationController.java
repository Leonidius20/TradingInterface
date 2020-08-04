package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.translations.AddTranslationScreen;

import java.sql.SQLException;

public class AddTranslationController extends BaseController {

    public AddTranslationController(ScreenManager manager) {
        super(manager);
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AddTranslationScreen(this), true);
    }

    public void submitInput(String itemId, String translation) {
        Item gameItem;
        if (itemId.isEmpty() || translation.isEmpty()
                || (gameItem = Item.fromString(itemId)).getId() == 0) {
            showErrorScreen(Message.WDW_INVALID_PARAMS.getText());
            return;
        }

        try {
            Dao<Translation, Integer> translationDao =
                    DaoManager.createDao(Trading.getSource(), Translation.class);
            if (translationDao.queryForEq("item_id", itemId).size() != 0) {
                showErrorScreen(Message.WDW_ADD_CUSTOM_NAME_FAIL.getText());
                return;
            }

            Translation translationModel = new Translation(itemId, translation);
            translationDao.create(translationModel);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_TRANSLATION_ADDED.log(manager.getPlayer().getName(),
                        gameItem.getName(), itemId, translation);
            }
        } catch (SQLException e) {
            handleException(e);
            return;
        }

        manager.back();
    }

}
