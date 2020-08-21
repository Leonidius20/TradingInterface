package ua.leonidius.trdinterface.controllers.sell;

import cn.nukkit.item.Item;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.AmountSelectorController;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.utils.ItemCompare;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.AmountSelectorScreen;

public class SellAmountSelectorController extends AmountSelectorController {

    private final SellableItem item;

    public SellAmountSelectorController(ScreenManager manager, SellableItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AmountSelectorScreen(this,
                Message.WDW_SELL_TITLE.getText(item.getName()),
                buildItemDescription(), getMaxAmount()), true);
    }

    @Override
    public void selectAmount(int amount) {
        Item gameItem = item.toGameItem();
        gameItem.setCount(amount);

        double cost = item.getPrice() * amount;

        manager.getPlayer().getInventory().removeItem(gameItem);
        EconomyAPI.getInstance().addMoney(manager.getPlayer(), cost);

        if (!manager.getPlayer().getInventory().contains(gameItem)) {
            showErrorScreen(Message.WDW_SELL_NOTHING.getText());
            return;
        }

        // Success
        if (Trading.getSettings().logTransactions()) {
            Message.LOG_SOLD.log(manager.getPlayer().getName(), amount,
                    gameItem.getName(), item.getItemId(),
                    cost, Trading.getSettings().getCurrency());
        }

        new InfoController(manager, Message.WDW_SUCCESS_TITLE.getText(),
                Message.SELL_SUCCESS.getText(amount, gameItem.getName(),
                        cost, Trading.getSettings().getCurrency())).showScreen();
    }

    @Override
    protected ShopItem getItem() {
        return item;
    }

    public int getMaxAmount() {
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            Item itemInSlot = manager.getPlayer().getInventory().getItem(i);
            if (ItemCompare.equals(itemInSlot, item.toGameItem())) {
                amount += itemInSlot.getCount();
            }
        }
        return amount;
    }

}
