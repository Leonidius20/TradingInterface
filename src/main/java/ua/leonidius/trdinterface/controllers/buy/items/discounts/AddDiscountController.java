package ua.leonidius.trdinterface.controllers.buy.items.discounts;

import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.utils.TimeUnit;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.edit.AddDiscountScreen;

public class AddDiscountController extends BaseController {

    private final BuyableItem item;

    public AddDiscountController(ScreenManager manager, BuyableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AddDiscountScreen(this), true);
    }

    /**
     * Adding a temporary discount to a buyable item
     *
     * @param percentS  string with the discount's size in percents
     * @param durationS string with the discount's duration in units, defined
     *                  by the 'multiplier' parameter
     * @param unit      units, in which the discount's duration is measured
     */
    public void addDiscount(String percentS, String durationS, TimeUnit unit) {
        double percent;
        double duration;

        try {
            percent = Double.parseDouble(percentS);

            if (percent == 0) {
                manager.back();
                return;
            }

            if (percent > 100 || percent < 0) {
                showErrorScreen(Message.WDW_EDIT_DISCOUNT_MORE_THAN_100.getText());
                return;
            }

            duration = Double.parseDouble(durationS);
            long creationTime = System.currentTimeMillis() / 1000L;
            long endTime = creationTime + (long) (duration * unit.getMultiplier());

            Discount discount = new Discount(item, percent, endTime);

            item.addDiscount(discount);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_DISCOUNT_ADDED.log(manager.getPlayer().getName(),
                        percent, item.getName(),
                        item.getItemId(), duration, unit.getName());
            }

            manager.back();
        } catch (NumberFormatException e) {
            showErrorScreen(Message.WDW_INVALID_PARAMS.getText());
        }
    }

    /**
     * Returns time units for AddDiscountScreen's dropdown menu
     *
     * @return array of time units
     * @see AddDiscountScreen
     */
    public TimeUnit[] getTimeUnits() {
        return TimeUnit.values();
    }

}
