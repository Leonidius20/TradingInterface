/*package ua.leonidius.trdinterface.controllers.sell.edit;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.elements.ItemButton;
import ua.leonidius.trdinterface.views.screens.MainScreen;
import ua.leonidius.trdinterface.views.screens.Screen;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class EditSellListScreen extends FormWindowSimple implements Screen {

    public EditSellListScreen() {
        super(Message.MENU_EDIT_SELL_LIST.getText(), "");

        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_ADD_ITEM.getText()));

        for (String key : Trading.sellCfg.getKeys()) {
            addButton(new ItemButton(key.replace("-", ":")));
        }

    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (getResponse().getClickedButtonId() == 0) { // Back
            player.showFormWindow(new MainScreen(player.hasPermission("shop.edit")));
        } else if (getResponse().getClickedButtonId() == 1) { // Add item
            player.showFormWindow(new AddSellItemScreen());
        } else { // Edit item
            ItemButton button = (ItemButton)getResponse().getClickedButton();
            String key = button.getStringId().replace(":", "-");
            player.showFormWindow(new SellItemMenuScreen(key));
        }
    }

}*/