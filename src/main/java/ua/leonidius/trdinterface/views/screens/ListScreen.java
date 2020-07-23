package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

import java.util.Map;

/**
 * Created by Leonidius20 on 26.06.18.
 */
public class ListScreen<T> extends SimpleScreen {

    private final transient ListController<T> controller;
    private final transient String emptyContent;
    private final transient String notEmptyContent;
    private final transient Map<String, ButtonCallback> additionalButtons;

    public ListScreen(ListController<T> controller,
                      String emptyContent,
                      Map<String, ButtonCallback> additionalButtons) {
        this(controller, emptyContent, "", additionalButtons);
    }

    /**
     * A screen that allows to choose an item from a provided list of items.
     * Parameter T represents the type of items in the list.
     * @param controller Controller for this screen
     * @param emptyContent Text to show if the list is empty
     * @param notEmptyContent Text to show if the list is not empty
     * @param additionalButtons Action buttons to show on top of the list
     */
    public ListScreen(ListController<T> controller,
                      String emptyContent, String notEmptyContent,
                      Map<String, ButtonCallback> additionalButtons) {
        super(controller.getTitle());
        this.controller = controller;
        this.emptyContent = emptyContent;
        this.notEmptyContent = notEmptyContent;
        this.additionalButtons = additionalButtons;
        update();
    }

    @Override
    public void update() {
        setTitle(controller.getTitle());

        getButtons().clear();

        // Back button
        addButton(new CallbackButton(Message.BTN_BACK.getText(), controller::back));

        if (additionalButtons != null) {
            additionalButtons.forEach((String name, ButtonCallback action) ->
                    addButton(new CallbackButton(name, action::call)));
        }

        T[] items = controller.fetchItems();
        if (items.length == 0) {
            setContent(emptyContent);
        } else {
            for (T item : controller.fetchItems()) {
                addButton(new CallbackButton(controller.buildItemButtonText(item),
                        () -> controller.selectItem(item)));
            }
            setContent(notEmptyContent);
        }

    }

    public interface ButtonCallback {
        void call();
    }

}
