package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ItemDetailsEditController;

public class ItemDetailsEditScreen extends CustomScreen {

    private final transient ItemDetailsEditController controller;

    public ItemDetailsEditScreen(ItemDetailsEditController controller, String title) {
        this(controller, title, "", "", "", "");
    }

    public ItemDetailsEditScreen(ItemDetailsEditController controller, String title,
                                 String itemId, String price,
                                 String customName, String customLore) {
        super(title);

        this.controller = controller;

        addElement(new ElementInput(Message.WDW_ADD_ITEM_ID.getText(),
                "", itemId)); // 0
        addElement(new ElementInput(Message.WDW_ADD_ITEM_PRICE.getText(),
                "", price)); // 1
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_NAME.getText(),
                "", customName)); // 2
        addElement(new ElementInput(Message.WDW_ADD_ITEM_CUSTOM_LORE.getText(),
                "", customLore)); // 3
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
        controller.submitDetails(itemId, price, customName, customLore);
    }

}
