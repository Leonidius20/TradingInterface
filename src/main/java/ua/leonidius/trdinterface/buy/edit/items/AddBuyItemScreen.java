package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyItemScreen extends FormWindowCustom implements Screen {

    String categoryId;

    public AddBuyItemScreen(String categoryId) {
        super(Message.WDW_ADD_ITEM_TITLE.getText());
        this.categoryId = categoryId;
        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText()));
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText()));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            String id = getResponse().getInputResponse(0);
            String key = id.replace(":", "-");

            if (buyCfg.getSection(categoryId).getSection("items").exists(key)) {
                event.getPlayer().showFormWindow(new AddBuyItemFailScreen(categoryId, AddBuyItemFailScreen.alreadyExists));
                return;
            }

            double price = Double.parseDouble(getResponse().getInputResponse(1).replace(",", "."));

            buyCfg.getSection(categoryId).getSection("items").set(key, new ConfigSection("price", price));
            buyCfg.save();

            if (settings.editLogging) {
                Message.LOG_BUY_ITEM_ADDED.log(event.getPlayer().getName(), ItemName.get(id), id, price, settings.currency);
            }

            event.getPlayer().showFormWindow(new AddBuyItemSuccessScreen(ItemName.get(id), categoryId));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new AddBuyItemFailScreen(categoryId, AddBuyItemFailScreen.invalidParams));
        }
    }
}
