/*package ua.leonidius.trdinterface.sell.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.sellCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 10.08.18.
 */
/*public class AddSellItemScreen extends FormWindowCustom implements Screen {

    public AddSellItemScreen() {
        super(Message.WDW_ADD_ITEM_TITLE.getText());
        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText()));
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            String id = getResponse().getInputResponse(0);
            String key = id.replace(":", "-");
            double price = Double.parseDouble(getResponse().getInputResponse(1).replace(",", "."));

            if (sellCfg.exists(key)) {
                event.getPlayer().showFormWindow(new AddSellItemFailScreen(AddSellItemFailScreen.alreadyExists));
                return;
            }

            Item item = Item.fromString(id);
            if (sellCfg.exists(item.getId() + "-" + item.getDamage())) {
                event.getPlayer().showFormWindow(new AddSellItemFailScreen(AddSellItemFailScreen.alreadyExists));
                return;
            }

            sellCfg.set(key, price);
            sellCfg.save();

            if (settings.editLogging) {
                Message.LOG_SELL_ITEM_ADDED.log(event.getPlayer().getName(), ItemName.get(id), id, price, settings.currency);
            }

            event.getPlayer().showFormWindow(new AddSellItemSuccessScreen(id));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new AddSellItemFailScreen(AddSellItemFailScreen.invalidParams));
        }
    }

}*/