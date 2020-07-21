package ua.leonidius.trdinterface.views.screens.buy.items.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.items.edit.AddBuyableItemController;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

/**
 * Created by Leonidius20 on 08.08.18.
 */
public class AddBuyableItemScreen extends CustomScreen {

    private final transient AddBuyableItemController controller;

    public AddBuyableItemScreen(AddBuyableItemController controller) {
        super(Message.WDW_ADD_ITEM_TITLE.getText());

        this.controller = controller;

        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText())); // 0
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText())); // 1
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_NAME.getText())); // 2
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_LORE.getText())); // 3
        addElement(new ElementLabel(Message.WDW_ADD_ITEM_ENCHANTMENTS.getText())); // 4
    }

    @Override
    public void update() {}

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        String itemId = getResponse().getInputResponse(0);
        String price = getResponse().getInputResponse(1);
        String customName = getResponse().getInputResponse(2);
        String customLore = getResponse().getInputResponse(3);
        controller.addItem(itemId, price, customName, customLore);
    }

}