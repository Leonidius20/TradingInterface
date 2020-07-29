package ua.leonidius.trdinterface.controllers.buy.discounts;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.ModalController;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.Discount;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.sql.SQLException;

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
        try {
            Dao<Discount, Integer> discountDao =
                    DaoManager.createDao(Trading.getSource(), Discount.class);
            discountDao.delete(item.getDiscount());

            if (Trading.getSettings().logEdits()) {
                Message.LOG_DISCOUNT_DELETED.log(manager.getPlayer().getName(),
                        item.getName(), item.getItemId());
            }

            manager.back();
        } catch (SQLException e) {
            handleException(e);
        }
    }

}
