package ua.leonidius.trdinterface.controllers.sell.edit;

import ua.leonidius.trdinterface.controllers.DeleteItemController;
import ua.leonidius.trdinterface.controllers.ItemDetailsViewController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.sell.edit.ManageSellableItemScreen;

public class ManageSellableItemController extends ItemDetailsViewController {

    private final SellableItem item;

    public ManageSellableItemController(ScreenManager manager, SellableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ManageSellableItemScreen(this));
    }

    @Override
    public boolean isBuy() {
        return false;
    }

    @Override
    protected SellableItem getItem() {
        return item;
    }

    public void editItem() {
        // TODO
    }

    public void manageEnchantments() {
        // TODO: universal controller
    }

    public void deleteItem() {
        new DeleteItemController(manager, item).showScreen();
    }

}
