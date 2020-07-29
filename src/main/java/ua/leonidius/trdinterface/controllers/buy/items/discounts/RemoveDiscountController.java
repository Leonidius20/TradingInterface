package ua.leonidius.trdinterface.controllers.buy.items.discounts;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ModalController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

public class RemoveDiscountController extends ModalController {

    private final BuyableItem item;

    public RemoveDiscountController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ModalScreen("",
                Message.WDW_REMOVE_DISCOUNT_CONF.getText(
                        item.getDiscount().getPercent(), item.getName()),
                this), true);
    }

    @Override
    public void onPositiveResponse() {
        item.removeDiscount();

        if (Trading.getSettings().logEdits()) {
            Message.LOG_DISCOUNT_DELETED.log(manager.getPlayer().getName(),
                    item.getName(), item.getItemId());
        }

        manager.back();
    }

}
