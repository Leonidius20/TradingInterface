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
public class AddBuyItemFailScreen extends FormWindowSimple implements Screen {

    String categoryId;
    final static boolean invalidParams = true, alreadyExists = false;

    public AddBuyItemFailScreen(String categoryId, boolean mode) {
        super(Message.WDW_FAIL_TITLE.getText(), "");

        if (mode == invalidParams) {
            setContent(Message.WDW_INVALID_PARAMS.getText());
        } else if (mode == alreadyExists) {
            setContent(Message.WDW_ITEM_ALREADY_EXISTS.getText());
        }

        this.categoryId = categoryId;
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new EditCategoryScreen(categoryId));
    }
}
