package ua.leonidius.trdinterface.views.elements;

import cn.nukkit.form.element.ElementButton;

public class CallbackButton extends ElementButton {

    private transient Callback callback;

    public CallbackButton(String text, Callback callback) {
        super(text);
        this.callback = callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void executeCallback() {
        callback.call();
    }

    public interface Callback {
        void call();
    }

}
