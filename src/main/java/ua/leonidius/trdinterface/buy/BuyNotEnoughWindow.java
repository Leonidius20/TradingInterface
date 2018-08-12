package ua.leonidius.trdinterface.buy;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 07.07.18.
 */
public class BuyNotEnoughWindow extends FormWindowSimple implements Screen {

    String categoryId;

    public BuyNotEnoughWindow(int code, String categoryId) {
        super(Message.WDW_FAIL_TITLE.getText(), "");
        this.categoryId = categoryId;
        switch (code) {
            case 0:
                setContent(Message.BUY_NO_SPACE_AND_MONEY.getText());
                break;
            case 1:
                setContent(Message.BUY_NO_MONEY.getText());
                break;
            case 2:
                setContent(Message.BUY_NO_SPACE.getText());
        }
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new BuyItemSelectorScreen(categoryId));
    }
}
