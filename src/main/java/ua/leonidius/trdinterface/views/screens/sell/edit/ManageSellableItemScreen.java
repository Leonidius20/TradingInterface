package ua.leonidius.trdinterface.views.screens.sell.edit;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.sell.edit.ManageSellableItemController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

public class ManageSellableItemScreen extends SimpleScreen {

    private final transient ManageSellableItemController controller;

    public ManageSellableItemScreen(ManageSellableItemController controller) {
        super(controller.showEditingButtons()
                ? Message.WDW_EDIT_ITEM_TITLE.getText()
                : Message.WDW_VIEWING_SELLABLE_ITEM.getText());
        this.controller = controller;

        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));

        if (controller.showEditingButtons()) {
            addButton(new CallbackButton(Message.BTN_EDIT_ITEM.getText(),
                    controller::editItem));
            addButton(new CallbackButton(Message.BTN_MANAGE_ENCHANTMENTS.getText(),
                    controller::manageEnchantments));
            addButton(new CallbackButton(Message.BTN_DELETE_ITEM.getText(),
                    controller::deleteItem));
        }
    }

    @Override
    public void update() {
        setContent(controller.buildItemDescription());
    }

}
