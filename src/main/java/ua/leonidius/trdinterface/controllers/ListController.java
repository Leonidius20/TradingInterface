package ua.leonidius.trdinterface.controllers;

import ua.leonidius.trdinterface.views.ScreenManager;

public abstract class ListController<T> extends BaseController {

    public ListController(ScreenManager manager) {
        super(manager);
    }

    public abstract T[] fetchItems();

    public abstract void selectItem(T item);

    public abstract String buildItemButtonText(T item);

    /**
     * Called in ListScreen's update() to refresh title
     * (e.g. after buyable items category rename)
     * @return title
     */
    public abstract String getTitle();

}
