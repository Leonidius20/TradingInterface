package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategorySuccessScreen extends FormWindowSimple implements Screen {

    public AddCategorySuccessScreen() {
        super(Message.WDW_SUCCESS_TITLE.getText(), Message.WDW_NEW_CATEGORY_SUCCESS.getText());
        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new EditCategoriesScreen());
    }
}
