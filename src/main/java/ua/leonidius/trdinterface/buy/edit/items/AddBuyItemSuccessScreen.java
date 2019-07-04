package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.BuyItemSelectorScreen;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyItemSuccessScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId;

    public AddBuyItemSuccessScreen(String itemName, int shopId, int categoryId) {
        super(Message.WDW_SUCCESS_TITLE.getText(), Message.WDW_ADD_BUY_ITEM_SUCCESS.getText(itemName));

        this.shopId = shopId;
        this.categoryId = categoryId;

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
    }

}