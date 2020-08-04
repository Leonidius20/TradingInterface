package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * Controller for the list of custom item translations.
 * The list is accessed from the main menu.
 */
public class TranslationsListController extends ListController<Translation> {

    ListScreen<Translation> screen;

    public TranslationsListController(ScreenManager manager) {
        super(manager);
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();
        buttons.put(Message.BTN_ADD_CUSTOM_NAME.getText(), this::addTranslation);
        manager.addAndShow(screen = new ListScreen<>(this,
                Message.WDW_CUSTOM_NAMES_HINT.getText(),
                Message.WDW_CUSTOM_NAMES_HINT.getText(), buttons));
    }

    @Override
    public Translation[] fetchItems() {
        try {
            Dao<Translation, Integer> translationDao =
                    DaoManager.createDao(Trading.getSource(), Translation.class);
            return translationDao.queryForAll().toArray(new Translation[0]);
        } catch (SQLException e) {
            Trading.printException(e);
            screen.setContent(screen.getContent() + '\n' + Message.ERROR.getText());
            return null;
        }

    }

    @Override
    public void selectItem(Translation item) {
        // TODO: translation adding/deleting logging
    }

    @Override
    public String buildItemButtonText(Translation translation) {
        return Item.fromString(translation.getItemId()).getName()
                + "-" + translation.getTranslation();
    }

    @Override
    public String getTitle() {
        return Message.MENU_CUSTOM_NAMES.getText();
    }

    private void addTranslation() {
        new AddTranslationController(manager).showScreen();
    }

}
