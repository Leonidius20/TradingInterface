package ua.leonidius.trdinterface.controllers.buy.items.discounts;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.utils.TimeUnit;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.edit.AddDiscountScreen;

import java.sql.SQLException;

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

            Dao<Discount, Integer> discountDao =
                    DaoManager.createDao(Trading.getSource(), Discount.class);

            discountDao.create(discount);

            if (Trading.getSettings().logEdits()) {
                Message.LOG_DISCOUNT_ADDED.log(manager.getPlayer().getName(),
                        percent, item.getName(),
                        item.getItemId(), duration, unit.getName());
            }

            manager.back();
        } catch (NumberFormatException e) {
            showErrorScreen(Message.WDW_INVALID_PARAMS.getText());
        } catch (SQLException e) {
            handleException(e);
        }


        /*try {
            // For logging
            int oldDiscount = 0;
            if (discountExisted) {
                oldDiscount = buyCfg.getSection(categoryId).getSection("items").getSection(key).getInt("discount");
            }
            String playerName = event.getPlayer().getName();
            String id = key.replace("-", ":");

            int discount = Integer.parseInt(getResponse().getInputResponse(0));

            if (discount > 100) {
                event.getPlayer().showFormWindow(new EditDiscountFailScreen(categoryId, key, EditDiscountFailScreen.moreThan100));
                return;
            }

            if (discount == 0) {
                buyCfg.getSection(categoryId).getSection("items").getSection(key).remove("discount");

                // Logging
                if (settings.editLogging && discountExisted) {
                    Message.LOG_DISCOUNT_DELETED.log(playerName, ItemName.get(id), id);
                }

            } else {
                buyCfg.getSection(categoryId).getSection("items").getSection(key).set("discount", discount);

                // logging
                if (settings.editLogging) {
                    if (discountExisted) {
                        Message.LOG_DISCOUNT_EDITED.log(playerName, ItemName.get(id), id, discount, oldDiscount);
                    } else {
                        Message.LOG_DISCOUNT_ADDED.log(playerName, discount, ItemName.get(id), id);
                    }
                }
            }

            buyCfg.save();

            event.getPlayer().showFormWindow(new BuyManageItemScreen(categoryId, key));
        } catch (Exception e) {
            event.getPlayer().showFormWindow(new EditDiscountFailScreen(categoryId, key, EditDiscountFailScreen.invalidParams));
        }*/
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
