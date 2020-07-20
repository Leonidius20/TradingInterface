package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class SimpleScreen extends FormWindowSimple implements Screen {

    private transient ScreenManager manager;
    private transient boolean ignoresStack;

    public SimpleScreen(ScreenManager manager) {
        super("", "");
        this.manager = manager;
    }

    public SimpleScreen(ScreenManager manager, String title) {
        super(title, "");
        this.manager = manager;
    }

    public SimpleScreen(ScreenManager manager, String title, String content) {
        super(title, content);
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

    /*@Override
    public void onClose(PlayerFormRespondedEvent event) {
        manager.onClose();
        manager = null;
    }*/

    @Override
    public void setIgnoreStack(boolean ingoresStack) {
        this.ignoresStack = ingoresStack;
    }

    @Override
    public boolean ignoresStack() {
        return ignoresStack;
    }

}