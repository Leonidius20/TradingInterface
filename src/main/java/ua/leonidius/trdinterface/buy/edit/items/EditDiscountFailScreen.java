/*package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class EditDiscountFailScreen extends FormWindowSimple implements Screen {

    String categoryId, key;
    final static boolean moreThan100 = false, invalidParams = true;

    public EditDiscountFailScreen(String categoryId, String key, boolean mode) {
        super(Message.WDW_FAIL_TITLE.getText(), "");

        this.categoryId = categoryId;
        this.key = key;

        if (mode == moreThan100) {
            setContent(Message.WDW_EDIT_DISCOUNT_MORE_THAN_100.getText());
        } else if (mode == invalidParams) {
            setContent(Message.WDW_INVALID_PARAMS.getText());
        }

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new BuyItemMenuScreen(categoryId, key));
    }

}*/