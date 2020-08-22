package ua.leonidius.trdinterface.views.screens;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ListController;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

import java.util.Collection;
import java.util.Map;

/**
 * A screen that allows to choose an item from a provided list of items,
 * and optionally shows extra action buttons on top of the list.
 *
 * @param <T> represents the type of items in the list
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

        Collection<T> items = controller.fetchItems();
        if (items == null) setContent(Message.ERROR.getText());
        else if (items.isEmpty()) setContent(emptyContent);
        else {
            for (T item : items) {
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
