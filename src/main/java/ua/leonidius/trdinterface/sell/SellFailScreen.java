package ua.leonidius.trdinterface.sell;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

public class SellFailScreen extends FormWindowSimple implements Screen {

    public SellFailScreen() {
        super(Message.WDW_FAIL_TITLE.getText(), Message.WDW_SELL_NOTHING.getText());
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new SellItemSelectorScreen(event.getPlayer().getInventory()));
    }
}