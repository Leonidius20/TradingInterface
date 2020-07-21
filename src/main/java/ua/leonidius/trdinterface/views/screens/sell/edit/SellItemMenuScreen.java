/*package ua.leonidius.trdinterface.controllers.sell.edit;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.views.screens.Screen;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class SellItemMenuScreen extends FormWindowSimple implements Screen {

    String key;

    public SellItemMenuScreen(String key) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText(), "");
        this.key = key;

        String itemName = ItemName.get(key.replace("-", ":"));
        double price = Trading.sellCfg.getDouble(key);

        setContent(Message.SELL_ITEM_INFO.getText(itemName, price, Trading.settings.currency));

        addButton(new ElementButton(Message.BTN_BACK.getText()));
        addButton(new ElementButton(Message.BTN_EDIT_ITEM.getText()));
        addButton(new ElementButton(Message.BTN_DELETE_ITEM.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int buttonId = getResponse().getClickedButtonId();
        Player player = event.getPlayer();

        if (buttonId == 0) { // Back
            player.showFormWindow(new EditSellListScreen());
        } else if (buttonId == 1) { // Edit item
            player.showFormWindow(new EditSellItemScreen(key));
        } else if (buttonId == 2) { // Delete item
            player.showFormWindow(new DeleteSellItemScreen(key));
        }
    }

}*/