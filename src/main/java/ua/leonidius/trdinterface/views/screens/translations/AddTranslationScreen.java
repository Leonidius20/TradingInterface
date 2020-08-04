package ua.leonidius.trdinterface.views.screens.translations;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.translations.AddTranslationController;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

/**
 * Created by Leonidius20 on 11.08.18.
 */
public class AddTranslationScreen extends CustomScreen {

    private transient final AddTranslationController controller;

    public AddTranslationScreen(AddTranslationController controller) {
        super(Message.WDW_ADD_CUSTOM_NAME_TITLE.getText());
        this.controller = controller;
        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText()));      // 0
        addElement(new ElementInput(Message.WDW_CUSTOM_NAME_HINT.getText())); // 1
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String itemId = getResponse().getInputResponse(0);
        String translation = getResponse().getInputResponse(1);
        controller.submitInput(itemId, translation);
    }

}
