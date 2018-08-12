package ua.leonidius.trdinterface.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;

/**
 * Created by Leonidius20 on 11.08.18.
 */
public class CustomNameMenuScreen extends FormWindowSimple implements Screen {

    String key;

    public CustomNameMenuScreen(String key) {
        super(Message.WDW_CUSTOM_NAME_TITLE.getText(), "");

        this.key = key;

        String id = key.replace("-", ":");
        String originalName = Item.fromString(id).getName();
        String customName = ItemName.get(id);
        setContent(Message.WDW_CUSTOM_NAME_INFO.getText(id, originalName, customName));

        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_EDIT_CUSTOM_NAME.getText()));
        addButton(new ElementButton(Message.BTN_DELETE_CUSTOM_NAME.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int id = getResponse().getClickedButtonId();
        if (id == 0) { // Back
            event.getPlayer().showFormWindow(new CustomNamesScreen());
        } else if (id == 1) { // Edit name
            event.getPlayer().showFormWindow(new EditCustomNameScreen(key));
        } else if (id == 2) { // Delete name
            event.getPlayer().showFormWindow(new DeleteCustomNameScreen(key));
        }
    }
}
