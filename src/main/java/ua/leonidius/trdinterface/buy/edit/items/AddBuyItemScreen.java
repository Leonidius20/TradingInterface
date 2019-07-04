package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopEditor;
import ua.leonidius.trdinterface.screens.Screen;

import static ua.leonidius.trdinterface.Trading.settings;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyItemScreen extends FormWindowCustom implements Screen {

    private int shopId, categoryId;

    public AddBuyItemScreen(int shopId, int categoryId) {
        super(Message.WDW_ADD_ITEM_TITLE.getText());

        this.shopId = shopId;
        this.categoryId = categoryId;

        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText())); // 0
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText())); // 1
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_NAME.getText())); // 2
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_LORE.getText())); // 3
        addElement(new ElementLabel(Message.WDW_ADD_ITEM_ENCHANTMENTS.getText())); // 4
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        try {
            Item item = Item.fromString(getResponse().getInputResponse(0));

            double price = Double.parseDouble(getResponse().getInputResponse(1).replace(",", "."));

            String customName = getResponse().getInputResponse(2);
            if (customName == null && !customName.equals("")) item.setCustomName(customName);

            String customLore = getResponse().getInputResponse(3);
            if (!customLore.equals("")) item.setLore(customLore);

            ShopEditor.addBuyItem(shopId, categoryId, item, price);

            if (settings.editLogging) {
                if (customName.equals("")) {
                    Message.LOG_BUY_ITEM_ADDED.log(event.getPlayer().getName(), item.getName(),
                            item.getId() + ":" + item.getDamage(), price, settings.currency);
                } else {
                    Message.LOG_BUY_ITEM_ADDED_WITH_CUSTOM_NAME.log(event.getPlayer().getName(), customName, item.getName(),
                            item.getId() + ":" + item.getDamage(), price, settings.currency);
                }
            }

            event.getPlayer().showFormWindow(new AddBuyItemSuccessScreen( item.getName(), shopId, categoryId));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new AddBuyItemFailScreen(shopId, categoryId));
        }
    }

}