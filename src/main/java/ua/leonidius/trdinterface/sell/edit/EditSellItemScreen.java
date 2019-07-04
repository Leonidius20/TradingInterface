/*package ua.leonidius.trdinterface.sell.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.sellCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class EditSellItemScreen extends FormWindowCustom implements Screen {

    String key;

    public EditSellItemScreen(String key) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText());
        this.key = key;
        String currentPrice = sellCfg.getString(key);
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText(), "", currentPrice));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            // for logging
            double oldPrice = sellCfg.getDouble(key);

            double newPrice = Double.parseDouble(getResponse().getInputResponse(0));

            sellCfg.set(key, newPrice);
            sellCfg.save();

            if (settings.editLogging) {
                String id = key.replace("-", ":");
                String playerName = event.getPlayer().getName();
                Message.LOG_SELL_ITEM_EDITED.log(playerName, ItemName.get(id), id, newPrice, settings.currency, oldPrice, settings.currency);
            }

            event.getPlayer().showFormWindow(new SellItemMenuScreen(key));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new EditSellItemFailScreen(key));
        }
    }

}*/