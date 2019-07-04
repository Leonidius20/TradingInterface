/*package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.ConfigSection;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import java.util.*;

import static ua.leonidius.trdinterface.Trading.buyCfg;
import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
/*public class EditBuyItemScreen extends FormWindowCustom implements Screen {

    String categoryId, key;

    public EditBuyItemScreen(String categoryId, String key) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText());

        this.categoryId = categoryId;
        this.key = key;

        String price = buyCfg.getSection(categoryId).getSection("items").getSection(key).getString("price");
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText(), "", price));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            double oldPrice = buyCfg.getSection(categoryId).getSection("items").getSection(key).getDouble("price");

            double newPrice = Double.parseDouble(getResponse().getInputResponse(0).replace(",", "."));

            buyCfg.getSection(categoryId).getSection("items").getSection(key).set("price", newPrice);
            buyCfg.save();

            if (settings.editLogging) {
                String id = key.replace("-", ":");
                String playerName = event.getPlayer().getName();
                Message.LOG_BUY_ITEM_EDITED.log(playerName, ItemName.get(id), id, newPrice, settings.currency, oldPrice, settings.currency);
            }

            event.getPlayer().showFormWindow(new BuyManageItemScreen(categoryId, key));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new AddBuyItemFailScreen(categoryId, AddBuyItemFailScreen.invalidParams));
        }
    }

}*/