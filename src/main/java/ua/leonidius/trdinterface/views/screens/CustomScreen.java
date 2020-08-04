package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.form.window.FormWindowCustom;

public abstract class CustomScreen extends FormWindowCustom implements Screen {

    private transient boolean ignoresStack = false;

    public CustomScreen() {
        super("");
    }

    public CustomScreen(String title) {
        super(title);
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
    public void update() {
    }

}