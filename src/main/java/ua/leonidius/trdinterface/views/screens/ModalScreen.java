package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;

public abstract class ModalScreen extends FormWindowModal implements Screen {

    private transient boolean ignoresStack = true;
    private transient final Callback trueCallback;
    private transient final Callback falseCallback;

    public ModalScreen(String title, String content,
                       String trueButtonText, Callback trueCallback,
                       String falseButtonText, Callback falseCallback) {
        super(title, content, trueButtonText, falseButtonText);
        this.trueCallback = trueCallback;
        this.falseCallback = falseCallback;
    }

    @Override
    public void setIgnoreStack(boolean ignoresStack) {
        this.ignoresStack = ignoresStack;
    }

    @Override
    public boolean ignoresStack() {
        return ignoresStack;
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Positive button clicked
            trueCallback.call();
        } else falseCallback.call();
    }

    public interface Callback {
        void call();
    }

}