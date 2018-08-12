package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class RenameCategoryScreen extends FormWindowCustom implements Screen {

    String categoryId;

    public RenameCategoryScreen(String categoryId) {
        super(Message.WDW_RENAME_CATEGORY_TITLE.getText());
        this.categoryId = categoryId;
        String categoryName = buyCfg.getSection(categoryId).getString("name");
        addElement(new ElementInput(Message.WDW_RENAME_CATEGORY_NAME.getText(), "", categoryName));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String newName = getResponse().getInputResponse(0);
        String oldName = buyCfg.getSection(categoryId).getString("name");

        buyCfg.getSection(categoryId).set("name", newName);
        buyCfg.save();

        if (settings.editLogging) {
            Message.LOG_CATEGORY_RENAMED.log(event.getPlayer().getName(), oldName, categoryId, newName);
        }

        event.getPlayer().showFormWindow(new EditCategoryScreen(categoryId));
    }
}
