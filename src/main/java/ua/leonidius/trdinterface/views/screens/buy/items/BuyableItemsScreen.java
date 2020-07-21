package ua.leonidius.trdinterface.views.screens.buy.items;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.items.BuyableItemsController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyableItemsScreen extends SimpleScreen {

    private final transient BuyableItemsController controller;

    public BuyableItemsScreen(BuyableItemsController controller) {
        super(controller.getCategoryName());
        this.controller = controller;
        update();
    }

    @Override
    public void update() {
        setTitle(controller.getCategoryName());
        getButtons().clear();
        setContent("");

        // Back button
        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));

        if (controller.showEditingButtons()) {
            addButton(new CallbackButton(Message.BTN_RENAME_CATEGORY.getText(),
                    controller::renameCategory));
            addButton(new CallbackButton(Message.BTN_DELETE_CATEGORY.getText(),
                    controller::deleteCategory));
            addButton(new CallbackButton(Message.BTN_ADD_ITEM.getText(),
                    controller::addItem));
        }

        BuyableItem[] items = controller.fetchItems();
        if (items.length == 0) {
            setContent(Message.WDW_BUY_EMPTY_CAT.getText());
        } else {
            for (BuyableItem item : controller.fetchItems()) {
                addButton(new CallbackButton(controller.buildItemButtonText(item),
                        () -> controller.buyItem(item)));
            }
        }

    }

}
