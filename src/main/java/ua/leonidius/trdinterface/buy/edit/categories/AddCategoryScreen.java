package ua.leonidius.trdinterface.buy.edit.categories;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

import java.util.LinkedHashMap;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddCategoryScreen extends FormWindowCustom implements Screen {

    public AddCategoryScreen() {
        super(Message.WDW_NEW_CATEGORY.getText());
        addElement(new ElementInput(Message.WDW_NEW_CATEGORY_ID.getText()));
        addElement(new ElementInput(Message.WDW_NEW_CATEGORY_NAME.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String categoryId = getResponse().getInputResponse(0);
        String name = getResponse().getInputResponse(1);

        if (categoryId.equals("") || name.equals("")) {
            event.getPlayer().showFormWindow(new AddCategoryFailScreen(AddCategoryFailScreen.incorrectParams));
            return;
        }

        if (buyCfg.exists(categoryId)) {
            event.getPlayer().showFormWindow(new AddCategoryFailScreen(AddCategoryFailScreen.alreadyExists));
            return;
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", name);
        map.put("items", new ConfigSection());
        ConfigSection categorySection = new ConfigSection(map);

        buyCfg.set(categoryId, categorySection);
        buyCfg.save();

        if (settings.editLogging) {
            Message.LOG_CATEGORY_ADDED.log(event.getPlayer().getName(), name, categoryId);
        }

        event.getPlayer().showFormWindow(new AddCategorySuccessScreen());

    }
}
