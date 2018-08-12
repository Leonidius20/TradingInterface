package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

/**
 * Created by Leonidius20 on 11.08.18.
 */
public class EditCustomNameScreen extends FormWindowCustom implements Screen {

    String key;

    public EditCustomNameScreen(String key) {
        super(Message.WDW_EDIT_CUSTOM_NAME_TITLE.getText());
        this.key = key;
        addElement(new ElementInput(Message.WDW_CUSTOM_NAME_HINT.getText(), "", Trading.customNames.getString(key)));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String name = getResponse().getInputResponse(0);
        Trading.customNames.set(key, name);
        Trading.customNames.save();
        event.getPlayer().showFormWindow(new CustomNameMenuScreen(key));
    }
}
