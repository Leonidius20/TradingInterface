package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.edit.categories.EditCategoryScreen;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 10.08.18.
 */
public class DeleteItemScreen extends FormWindowModal implements Screen {

    String categoryId, key;

    public DeleteItemScreen(String categoryId, String key) {
        super(Message.WDW_DELETE_ITEM_TITLE.getText(), Message.WDW_DELETE_BUY_ITEM_CONF.getText(ItemName.get(key.replace("-", ":"))), Message.BTN_YES.getText(), Message.BTN_NO.getText());
        this.key = key;
        this.categoryId = categoryId;
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Yes
            buyCfg.getSection(categoryId).getSection("items").remove(key);
            buyCfg.save();

            if (settings.editLogging) {
                String id = key.replace("-", ":");
                Message.LOG_BUY_ITEM_DELETED.log(event.getPlayer().getName(), ItemName.get(id), id);
            }

            event.getPlayer().showFormWindow(new EditCategoryScreen(categoryId));
        } else { // No
            event.getPlayer().showFormWindow(new BuyItemMenuScreen(categoryId, key));
        }
    }
}
