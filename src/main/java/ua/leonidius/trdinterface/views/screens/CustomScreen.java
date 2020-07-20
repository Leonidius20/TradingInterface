package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class CustomScreen extends FormWindowCustom implements Screen {

    private transient ScreenManager manager;
    private transient boolean ignoresStack;

    public CustomScreen(ScreenManager manager) {
        super("");
        this.manager = manager;
    }

    public CustomScreen(ScreenManager manager, String title) {
        super(title);
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