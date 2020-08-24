package ua.leonidius.trdinterface.controllers.sell.edit;

import ua.leonidius.trdinterface.controllers.DeleteItemController;
import ua.leonidius.trdinterface.controllers.EditItemController;
import ua.leonidius.trdinterface.controllers.ItemDetailsViewController;
import ua.leonidius.trdinterface.controllers.ManageEnchantmentsController;
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

    public boolean showEditingButtons() {
        // TODO: check divided permissions
        return manager.getPlayer().hasPermission("shop.edit");
    }

    @Override
    protected SellableItem getItem() {
        return item;
    }

    public void editItem() {
        new EditItemController(manager, item).showScreen();
    }

    public void manageEnchantments() {
        new ManageEnchantmentsController(manager, item).showScreen();
    }

    public void deleteItem() {
        new DeleteItemController(manager, item).showScreen();
    }

}
