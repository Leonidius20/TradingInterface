package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.elements.CategoryButton;
import ua.leonidius.trdinterface.screens.MainScreen;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class EditCategoriesScreen extends FormWindowSimple implements Screen {

    public EditCategoriesScreen() {
        super(Message.MENU_EDIT_BUY_LIST.getText(), "");
        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_ADD_CATEGORY.getText()));

        for (String key : Trading.buyCfg.getKeys(false)) {
            String categoryName = Trading.buyCfg.getSection(key).getString("name");
            addButton(new CategoryButton(categoryName, key));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (getResponse().getClickedButtonId() == 0) { // Back button
            player.showFormWindow(new MainScreen(player.hasPermission("shop.edit")));
        } else if (getResponse().getClickedButtonId() == 1) { // Add button
            player.showFormWindow(new AddCategoryScreen());
        } else {
            CategoryButton button = (CategoryButton)getResponse().getClickedButton();
            player.showFormWindow(new EditCategoryScreen(button.getCategoryId()));
        }
    }
}
