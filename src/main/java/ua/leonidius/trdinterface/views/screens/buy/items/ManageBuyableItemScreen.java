package ua.leonidius.trdinterface.views.screens.buy.items;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.items.edit.ManageBuyableItemController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

/**
 * This screen is shown to players who have permissions to manage the shop. It allows to select an action
 * to perform on an item: buy, edit, delete, edit discount
 */
public class ManageBuyableItemScreen extends SimpleScreen {

    private final transient ManageBuyableItemController controller;

    public ManageBuyableItemScreen(ManageBuyableItemController controller) {
        super(Message.WDW_EDIT_ITEM_TITLE.getText());

        this.controller = controller;

        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));
        addButton(new CallbackButton(Message.BTN_BUY_ITEM.getText(),
                controller::buyItem));
        addButton(new CallbackButton(Message.BTN_EDIT_ITEM.getText(),
                controller::editItem));
        addButton(new CallbackButton(Message.BTN_EDIT_DISCOUNT.getText(),
                controller::editDiscount));
        addButton(new CallbackButton(Message.BTN_ADD_ENCHANTMENT.getText(),
                controller::addEnchantment));
        addButton(new CallbackButton(Message.BTN_REMOVE_ENCHANTMENT.getText(),
                controller::removeEnchantment));
        addButton(new CallbackButton(Message.BTN_DELETE_ITEM.getText(),
                controller::deleteItem));

        update();
    }

    @Override
    public void update() {
        setContent(controller.buildItemDescription());
    }

}