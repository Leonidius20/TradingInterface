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
public class AddBuyItemFailScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId;
    //final static boolean invalidParams = true, alreadyExists = false;

    public AddBuyItemFailScreen(int shopId, int categoryId) {
        super(Message.WDW_FAIL_TITLE.getText(), Message.WDW_INVALID_PARAMS.getText());

        this.shopId = shopId;
        this.categoryId = categoryId;

        /*if (mode == invalidParams) {
            setContent(Message.WDW_INVALID_PARAMS.getText());
        } else if (mode == alreadyExists) {
            setContent(Message.WDW_ITEM_ALREADY_EXISTS.getText());
        }*/

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        // TODO: possibly make a retrun to AddBuyItemScreen with the inputs kept
    }

}