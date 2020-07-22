package ua.leonidius.trdinterface.controllers.buy.items.edit;

import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.controllers.buy.items.BuyAmountSelectorController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.ManageBuyableItemScreen;

public class ManageBuyableItemController extends BaseController {

    private final BuyableItem item;

    public ManageBuyableItemController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ManageBuyableItemScreen(this));
    }

    public String buildItemDescription() {
        item.resetGameItem();
        return item.buildDescription();
    }

    public void buyItem() {
        new BuyAmountSelectorController(manager, item).showScreen();
    }

    public void editItem() {
        new EditBuyableItemController(manager, item).showScreen();
    }

    public void editDiscount() {

    }

    public void addEnchantment() {

    }

    public void removeEnchantment() {

    }

    public void deleteItem() {
        new DeleteItemController<>(manager, item).showScreen();
    }

}
