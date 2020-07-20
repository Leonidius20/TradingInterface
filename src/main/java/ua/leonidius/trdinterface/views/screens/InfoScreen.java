package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;

/**
 * Universal success/error window that allows to go back to the previous window.
 */
public class InfoScreen extends SimpleScreen {

    public InfoScreen(ScreenManager manager, String message) {
        super("", message);
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        // getScreenManager().back();
    }

    @Override
    public void update() {}

}