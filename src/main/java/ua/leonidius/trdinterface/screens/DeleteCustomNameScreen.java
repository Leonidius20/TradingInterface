/*package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

/**
 * Created by Leonidius20 on 11.08.18.
 */
/*public class DeleteCustomNameScreen extends FormWindowModal implements Screen {

    String key;

    public DeleteCustomNameScreen(String key) {
        super(Message.WDW_DELETE_CUSTOM_NAME_TITLE.getText(), Message.WDW_DELETE_CUSTOM_NAME_CONF.getText(), Message.BTN_YES.getText(), Message.BTN_NO.getText());
        this.key = key;
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { //Yes
            Trading.customNames.remove(key);
            Trading.customNames.save();
            event.getPlayer().showFormWindow(new CustomNamesScreen());
        } else {
            event.getPlayer().showFormWindow(new CustomNameMenuScreen(key));
        }
    }
}*/
