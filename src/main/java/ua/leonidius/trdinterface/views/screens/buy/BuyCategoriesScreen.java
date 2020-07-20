package ua.leonidius.trdinterface.views.screens.buy;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.CategoriesController;
import ua.leonidius.trdinterface.models.Category;
import ua.leonidius.trdinterface.views.elements.CallbackButton;
import ua.leonidius.trdinterface.views.screens.SimpleScreen;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class BuyCategoriesScreen extends SimpleScreen {

    private final transient CategoriesController controller;

    public BuyCategoriesScreen(CategoriesController controller) {
        super(Message.WDW_BUY_CAT_TITLE.getText());
        this.controller = controller;
        update();
    }

    @Override
    public void update() {
        getButtons().clear();
        setContent("");

        // Back button
        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));

        boolean showEditingButtons = controller.showEditingButtons();

        if (showEditingButtons) {
            addButton(new CallbackButton(Message.BTN_ADD_CATEGORY.getText(),
                    controller::addCategory));
        }

        for (Category category : controller.fetchCategories()) {
            addButton(new CallbackButton(category.name,
                    () -> controller.selectCategory(category.recordId)));
        }

        if ((getButtons().size() == 1 && !showEditingButtons)
                || (getButtons().size() == 2 && showEditingButtons)) {
            setContent(Message.WDW_BUY_NO_CATEGORIES.getText());
        }
    }

}