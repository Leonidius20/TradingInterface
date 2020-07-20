package ua.leonidius.trdinterface.views.elements;

import cn.nukkit.form.element.ElementButton;

public class CallbackButton extends ElementButton {

    private final Callback callback;

    public CallbackButton(String text, Callback callback) {
        super(text);
        this.callback = callback;
    }

    public void executeCallback() {
        callback.call();
    }

    public interface Callback {
        void call();
    }

}
