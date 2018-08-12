package ua.leonidius.trdinterface.buy;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.elements.CategoryButton;
import ua.leonidius.trdinterface.screens.MainScreen;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyCategoriesScreen extends FormWindowSimple implements Screen {

    public BuyCategoriesScreen() {
        super(Message.WDW_BUY_CAT_TITLE.getText(), "");

        // Back button
        addButton(new ElementButton(Message.BTN_BACK.getText()));

        for (String key : Trading.buyCfg.getKeys(false)) {
            String categoryName = Trading.buyCfg.getSection(key).getString("name");
            addButton(new CategoryButton(categoryName, key));
        }

        if (getButtons().size() == 1) {
            setContent(Message.WDW_BUY_NO_CATEGORIES.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Back
            event.getPlayer().showFormWindow(new MainScreen(event.getPlayer().hasPermission("shop.edit")));
            return;
        }

        CategoryButton button = (CategoryButton)getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuyItemSelectorScreen(button.getCategoryId()));
    }
}
