package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ModalController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

public class DeleteTranslationController extends ModalController {

    private final Translation translation;

    public DeleteTranslationController(ScreenManager manager, Translation translation) {
        super(manager);
        this.translation = translation;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ModalScreen(
                Message.WDW_DELETE_CUSTOM_NAME_TITLE.getText(),
                Message.WDW_DELETE_CUSTOM_NAME_CONF.getText(),
                this), true);
    }

    @Override
    public void onPositiveResponse() {
        try {
            Dao<Translation, Integer> translationDao =
                    DaoManager.createDao(Trading.getSource(), Translation.class);

            translationDao.delete(translation);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_TRANSLATION_DELETED.log(manager.getPlayer().getName(),
                        Item.fromString(translation.getItemId()).getName(),
                        translation.getItemId());
            }

            manager.backTwoScreens();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
