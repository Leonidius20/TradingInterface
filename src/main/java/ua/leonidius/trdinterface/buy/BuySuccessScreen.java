package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
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

    private int shopId, categoryId;

    public BuySuccessScreen(int amount, String name, double cost, int shopId, int categoryId) {
        super(Message.WDW_SUCCESS_TITLE.getText(), Message.BUY_SUCCESS.getText(amount, name, cost, Trading.settings.currency));

        this.shopId = shopId;
        this.categoryId = categoryId;

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
    }

}