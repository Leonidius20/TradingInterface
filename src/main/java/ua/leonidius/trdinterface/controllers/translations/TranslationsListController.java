package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.LinkedHashMap;

/**
 * Controller for the list of custom item translations.
 * The list is accessed from the main menu.
 */
public class TranslationsListController extends ListController<Translation> {

    public TranslationsListController(ScreenManager manager) {
        super(manager);
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();
        buttons.put(Message.BTN_ADD_CUSTOM_NAME.getText(), this::addTranslation);
        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_CUSTOM_NAMES_HINT.getText(),
                Message.WDW_CUSTOM_NAMES_HINT.getText(), buttons));
    }

    @Override
    public Translation[] fetchItems() {
        /*try {
            Dao<Translation, Integer> translationDao =
                    DaoManager.createDao(Trading.getSource(), Translation.class);
            return translationDao.queryForAll().toArray(new Translation[0]);
        } catch (SQLException e) {

            handleException(e);

        }*/
        return new Translation[0];
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
        return Message.WDW_CUSTOM_NAME_TITLE.getText();
    }

    private void addTranslation() {
        // TODO
    }

}
