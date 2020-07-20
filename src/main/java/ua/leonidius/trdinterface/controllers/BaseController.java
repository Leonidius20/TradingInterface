package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.screens.Screen;

public abstract class BaseController<V extends Screen> {

    V view;

    BaseController(V view) {
        this.view = view;
    }

}
