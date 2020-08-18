package ua.leonidius.trdinterface.views.screens.translations;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.translations.TranslationMenuController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

/**
 * Created by Leonidius20 on 11.08.18.
 */
public class CustomNameMenuScreen extends SimpleScreen {

    private final transient TranslationMenuController controller;

    public CustomNameMenuScreen(TranslationMenuController controller) {
        super(Message.WDW_CUSTOM_NAME_TITLE.getText(), "");

        this.controller = controller;

        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));
        addButton(new CallbackButton(Message.BTN_EDIT_CUSTOM_NAME.getText(),
                controller::editTranslation));
        addButton(new CallbackButton(Message.BTN_DELETE_CUSTOM_NAME.getText(),
                controller::deleteTranslation));
    }

    @Override
    public void update() {
        setContent(controller.getContent());
    }

}
