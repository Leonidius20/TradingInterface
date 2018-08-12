package ua.leonidius.trdinterface.buy;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 07.07.18.
 */
public class BuySuccessScreen extends FormWindowSimple implements Screen {

    String categoryId;

    public BuySuccessScreen(String name, int amount, double cost, String categoryId) {
        super(Message.WDW_SUCCESS_TITLE.getText(), Message.BUY_SUCCESS.getText(amount, name, cost, Trading.settings.currency));
        this.categoryId = categoryId;
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new BuyItemSelectorScreen(categoryId));
    }
}
