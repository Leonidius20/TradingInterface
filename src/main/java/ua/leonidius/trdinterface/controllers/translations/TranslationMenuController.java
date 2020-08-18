package ua.leonidius.trdinterface.controllers.translations;

import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.Translation;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.translations.CustomNameMenuScreen;

public class TranslationMenuController extends BaseController {

    private final Translation translation;

    public TranslationMenuController(ScreenManager manager, Translation translation) {
        super(manager);
        this.translation = translation;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new CustomNameMenuScreen(this));
    }

    public String getContent() {
        return Message.WDW_CUSTOM_NAME_INFO.getText(translation.getItemId(),
                Item.fromString(translation.getItemId()).getName(),
                translation.getTranslation());
    }

    public void editTranslation() {
        new EditTranslationController(manager, translation).showScreen();
    }

    public void deleteTranslation() {
        new DeleteTranslationController(manager, translation).showScreen();
    }

}
