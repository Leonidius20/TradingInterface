package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.NamingController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.NamingScreen;

import java.sql.SQLException;

public class EditTranslationController extends NamingController {

    private final Translation translation;

    public EditTranslationController(ScreenManager manager, Translation translation) {
        super(manager);
        this.translation = translation;
    }

    @Override
    protected String getScreenTitle() {
        return Message.WDW_EDIT_CUSTOM_NAME_TITLE.getText();
    }

    @Override
    protected String getInputFieldHint() {
        return Message.WDW_CUSTOM_NAME_HINT.getText();
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new NamingScreen(this, getScreenTitle(),
                getInputFieldHint(), translation.getTranslation()), true);
    }

    @Override
    public void submitName(String newTranslation) {
        String oldTranslation = translation.getTranslation();

        if (newTranslation.equals(oldTranslation)) {
            manager.back();
            return;
        }

        translation.setTranslation(newTranslation);

        try {
            Dao<Translation, Integer> translationDao =
                    DaoManager.createDao(Trading.getSource(), Translation.class);

            translationDao.update(translation);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_TRANSLATION_CHANGED.log(manager.getPlayer().getName(),
                        Item.fromString(translation.getItemId()).getName(),
                        translation.getItemId(), oldTranslation, newTranslation);
            }

            manager.back();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
