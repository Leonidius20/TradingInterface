package ua.leonidius.trdinterface.screens;

import cn.nukkit.form.window.FormWindowModal;
import ua.leonidius.trdinterface.ScreenManager;

public abstract class ModalScreen extends FormWindowModal implements Screen {

    private transient ScreenManager manager;
    private transient boolean ignoresStack;

    public ModalScreen(ScreenManager manager, String title, String content, String trueButtonText, String falseButtonText) {
        super(title, content, trueButtonText, falseButtonText);
        this.manager = manager;
    }

    @Override
    public void setScreenManager(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public ScreenManager getScreenManager() {
        return manager;
    }

    @Override
    public void setIgnoreStack(boolean ingoresStack) {
        this.ignoresStack = ingoresStack;
    }

    @Override
    public boolean ignoresStack() {
        return ignoresStack;
    }

}