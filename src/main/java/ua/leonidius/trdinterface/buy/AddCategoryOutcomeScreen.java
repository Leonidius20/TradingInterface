package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategoryOutcomeScreen extends FormWindowSimple implements Screen {

    private int shopId;

    public AddCategoryOutcomeScreen(int shopId, boolean success) {
        super(success ? Message.WDW_SUCCESS_TITLE.getText() : Message.WDW_FAIL_TITLE.getText(), "");

        this.shopId = shopId;

        if (success) setContent(Message.WDW_NEW_CATEGORY_SUCCESS.getText());
        else setContent(Message.WDW_NEW_CATEGORY_FAIL.getText());

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyCategoriesScreen(shopId, player.hasPermission("shop.edit")));
    }

}