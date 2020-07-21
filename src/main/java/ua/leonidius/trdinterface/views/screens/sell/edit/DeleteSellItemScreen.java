/* ua.leonidius.trdinterface.controllers.sell.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.Screen;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class DeleteSellItemScreen extends FormWindowModal implements Screen {

    String key;

    public DeleteSellItemScreen(String key) {
        super(Message.WDW_DELETE_ITEM_TITLE.getText(), Message.WDW_DELETE_SELL_ITEM_CONF.getText(ItemName.get(key.replace("-", ":"))), Message.BTN_YES.getText(), Message.BTN_NO.getText());
        this.key = key;
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Yes
            Trading.sellCfg.remove(key);
            Trading.sellCfg.save();

            if (settings.editLogging) {
                String id = key.replace("-", ":");
                Message.LOG_SELL_ITEM_DELETED.log(event.getPlayer().getName(), ItemName.get(id), id);
            }

            event.getPlayer().showFormWindow(new EditSellListScreen());
        } else { // No
            event.getPlayer().showFormWindow(new SellItemMenuScreen(key));
        }
    }

}*/