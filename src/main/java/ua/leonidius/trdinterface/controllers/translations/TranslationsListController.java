package ua.leonidius.trdinterface.controllers.translations;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;

/**
 * Controller for the list of custom item translations.
 * The list is accessed from the main menu.
 */
public class TranslationsListController extends ListController<Translation> {

    public TranslationsListController(ScreenManager manager) {
        super(manager);
    }

    @Override
    public Translation[] fetchItems() {
        return new Translation[0];
    }

    @Override
    public void selectItem(Translation item) {

    }

    @Override
    public String buildItemButtonText(Translation item) {
        return null;
    }

    @Override
    public String getTitle() {
        return Message.WDW_CUSTOM_NAME_TITLE.getText();
    }

    @Override
    public void showScreen() {

    }

}
