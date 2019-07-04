package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 07.07.18.
 */
public class BuyFailScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId;

    // TODO: test if we can safely put final static variables here (id codes of failures)

    public BuyFailScreen(int code, int shopId, int categoryId) {
        super(Message.WDW_FAIL_TITLE.getText(), "");

        this.shopId = shopId;
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
            case 3:
                setContent(Message.ERROR.getText());
        }

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
    }

}