/*package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;

import static ua.leonidius.trdinterface.Trading.customNames;

/**
 * Created by Leonidius20 on 11.08.18.
 */
/*public class AddCustomNameScreen extends FormWindowCustom implements Screen {

    public AddCustomNameScreen() {
        super(Message.WDW_ADD_CUSTOM_NAME_TITLE.getText());
        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText()));
        addElement(new ElementInput(Message.WDW_CUSTOM_NAME_HINT.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String id = getResponse().getInputResponse(0);
        String name = getResponse().getInputResponse(1);

        String key = id.replace(":", "-");

        if (id.equals("") || name.equals("")) {
            event.getPlayer().showFormWindow(new AddCustomNameFailScreen(AddCustomNameFailScreen.incorrectParams));
            return;
        }

        if (customNames.exists(key)) {
            event.getPlayer().showFormWindow(new AddCustomNameFailScreen(AddCustomNameFailScreen.alreadyExists));
            return;
        }

        Item item = Item.fromString(id);
        if (customNames.exists(item.getId()+"-"+item.getDamage())) {
            event.getPlayer().showFormWindow(new AddCustomNameFailScreen(AddCustomNameFailScreen.alreadyExists));
            return;
        }

        customNames.set(key, name);
        customNames.save();
        event.getPlayer().showFormWindow(new CustomNamesScreen());
    }
}*/
