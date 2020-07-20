/*package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.elements.CustomNameButton;

/**
 * Created by Leonidius20 on 11.08.18.
 */
/*public class CustomNamesScreen extends FormWindowSimple implements Screen {

    public CustomNamesScreen() {
        super(Message.MENU_CUSTOM_NAMES.getText(), Message.WDW_CUSTOM_NAMES_HINT.getText());
        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_ADD_CUSTOM_NAME.getText()));
        for (String key : Trading.customNames.getKeys()) {
            addButton(new CustomNameButton(key));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (getResponse().getClickedButtonId() == 0) { // Back
            player.showFormWindow(new MainScreen(1));
        } else if (getResponse().getClickedButtonId() == 1) { // Add custom name
            player.showFormWindow(new AddCustomNameScreen());
        } else {
            CustomNameButton button = (CustomNameButton)getResponse().getClickedButton();
            player.showFormWindow(new CustomNameMenuScreen(button.key));
        }
    }
}
*/