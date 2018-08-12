package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class DeleteCategoryScreen extends FormWindowModal implements Screen {

    String categoryId;

    public DeleteCategoryScreen(String categoryId) {
        super(Message.WDW_DELETE_CATEGORY_TITLE.getText(), Message.WDW_DELETE_CATEGORY_CONF.getText(buyCfg.getSection(categoryId).getString("name")), Message.BTN_YES.getText(), Message.BTN_NO.getText());
        this.categoryId = categoryId;
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Yes
            String categoryName = buyCfg.getSection(categoryId).getString("name");

            buyCfg.remove(categoryId);
            buyCfg.save();

            if (settings.editLogging) {
                Message.LOG_CATEGORY_DELETED.log(event.getPlayer().getName(), categoryName, categoryId);
            }

            event.getPlayer().showFormWindow(new EditCategoriesScreen());
        } else { // No
            event.getPlayer().showFormWindow(new EditCategoryScreen(categoryId));
        }
    }
}
