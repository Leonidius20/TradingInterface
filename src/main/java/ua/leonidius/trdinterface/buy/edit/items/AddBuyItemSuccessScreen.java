package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.edit.categories.EditCategoryScreen;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyItemSuccessScreen extends FormWindowSimple implements Screen {

    String categoryId;

    public AddBuyItemSuccessScreen(String itemName, String categoryId) {
        super(Message.WDW_SUCCESS_TITLE.getText(), Message.WDW_ADD_BUY_ITEM_SUCCESS.getText(itemName));
        this.categoryId = categoryId;
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new EditCategoryScreen(categoryId));
    }
}
