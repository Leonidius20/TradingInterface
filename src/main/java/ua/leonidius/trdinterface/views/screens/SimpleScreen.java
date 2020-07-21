package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.views.elements.CallbackButton;

public abstract class SimpleScreen extends FormWindowSimple implements Screen {

    private transient boolean ignoresStack = false;

    public SimpleScreen() {
        super("", "");
    }

    public SimpleScreen(String title) {
        super(title, "");
    }

    public SimpleScreen(String title, String content) {
        super(title, content);
    }

    /*@Override
    public void onClose(PlayerFormRespondedEvent event) {
        manager.onClose();
        manager = null;
    }*/

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
        ElementButton button = getResponse().getClickedButton();
        if (button instanceof CallbackButton) {
            ((CallbackButton) button).executeCallback();
        }
    }
}