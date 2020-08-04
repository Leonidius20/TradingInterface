package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.InfoScreen;

public class InfoController extends BaseController {

    private final String title;
    private final String content;

    public InfoController(ScreenManager manager, String content) {
        super(manager);
        this.title = "";
        this.content = content;
    }

    public InfoController(ScreenManager manager, String title, String content) {
        super(manager);
        this.title = title;
        this.content = content;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new InfoScreen(this, title, content), true);
    }

}
