package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;

/**
 * Created by Leonidius20 on 11.08.18.
 */
public class AddCustomNameFailScreen extends FormWindowSimple implements Screen {

    final static boolean alreadyExists = true, incorrectParams = false;

    public AddCustomNameFailScreen(boolean mode) {
        super(Message.WDW_FAIL_TITLE.getText(), "");

        if (mode = alreadyExists) {
            setContent(Message.WDW_ADD_CUSTOM_NAME_FAIL.getText());
        } else if (mode = incorrectParams) {
            setContent(Message.WDW_INVALID_PARAMS.getText());
        }

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new CustomNamesScreen());
    }
}
