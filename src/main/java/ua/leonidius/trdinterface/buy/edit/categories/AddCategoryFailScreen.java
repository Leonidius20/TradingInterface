package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategoryFailScreen extends FormWindowSimple implements Screen {

    final static boolean alreadyExists = true, incorrectParams = false;

    public AddCategoryFailScreen(boolean mode) {
        super(Message.WDW_FAIL_TITLE.getText(), "");

        if (mode == alreadyExists) {
            setContent(Message.WDW_NEW_CATEGORY_FAIL.getText());
        } else if (mode == incorrectParams) {
            setContent(Message.WDW_INVALID_PARAMS.getText());
        }

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new EditCategoriesScreen());
    }
}
