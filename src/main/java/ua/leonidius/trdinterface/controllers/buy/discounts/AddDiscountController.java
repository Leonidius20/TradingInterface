package ua.leonidius.trdinterface.controllers.buy.discounts;

import ua.leonidius.trdinterface.controllers.BaseController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Discount;
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
        manager.addAndShow(new AddDiscountScreen(this));
    }

    /**
     * Adding a temporary discount to a buyable item
     *
     * @param percentS   string with the discount's size in percents
     * @param durationS  string with the discount's duration in units, defined
     *                   by the 'multiplier' parameter
     * @param multiplier defines units, in which the discount's duration is
     *                   measured, duration * multiplier = duration in seconds
     */
    public void addDiscount(String percentS, String durationS, int multiplier) {
        Discount discount = new Discount();



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

}
