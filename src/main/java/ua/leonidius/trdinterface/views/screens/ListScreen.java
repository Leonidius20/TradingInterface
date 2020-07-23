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
    private final transient Map<String, ButtonCallback> additionalButtons;

    public ListScreen(ListController<T> controller,
                      String emptyContent,
                      Map<String, ButtonCallback> additionalButtons) {
        super(controller.getTitle());
        this.controller = controller;
        this.emptyContent = emptyContent;
        this.additionalButtons = additionalButtons;
        update();
    }

    @Override
    public void update() {
        setTitle(controller.getTitle());

        getButtons().clear();
        setContent("");

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
        }

    }

    public interface ButtonCallback {
        void call();
    }

}
