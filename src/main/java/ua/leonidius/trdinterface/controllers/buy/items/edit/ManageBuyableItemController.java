package ua.leonidius.trdinterface.controllers.buy.items.edit;

import ua.leonidius.trdinterface.controllers.DeleteItemController;
import ua.leonidius.trdinterface.controllers.EditItemController;
import ua.leonidius.trdinterface.controllers.ItemDetailsViewController;
import ua.leonidius.trdinterface.controllers.ManageEnchantmentsController;
import ua.leonidius.trdinterface.controllers.buy.items.BuyAmountSelectorController;
import ua.leonidius.trdinterface.controllers.buy.items.discounts.AddDiscountController;
import ua.leonidius.trdinterface.controllers.buy.items.discounts.RemoveDiscountController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.edit.ManageBuyableItemScreen;

public class ManageBuyableItemController extends ItemDetailsViewController {

    private final BuyableItem item;

    public ManageBuyableItemController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ManageBuyableItemScreen(this));
    }

    @Override
    protected BuyableItem getItem() {
        return item;
    }

    public boolean hasDiscount() {
        return item.getDiscount() != null;
    }

    public void buyItem() {
        new BuyAmountSelectorController(manager, item).showScreen();
    }

    public void editItem() {
        new EditItemController(manager, item).showScreen();
    }

    public void addDiscount() {
        new AddDiscountController(manager, item).showScreen();
    }

    public void removeDiscount() {
        new RemoveDiscountController(manager, item).showScreen();
    }

    public void manageEnchantments() {
        new ManageEnchantmentsController(manager, item).showScreen();
    }

    public void deleteItem() {
        new DeleteItemController(manager, item).showScreen();
    }

}
