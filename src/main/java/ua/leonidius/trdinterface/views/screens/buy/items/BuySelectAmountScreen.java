package ua.leonidius.trdinterface.views.screens.buy.items;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public class BuySelectAmountScreen extends CustomScreen {

    private transient int itemId;

    public BuySelectAmountScreen(ScreenManager manager, int itemId, int maxAmount) throws SQLException, IOException {
        //super(Message.WDW_BUY_TITLE.getText(itemName));
        super("ff"); // TODO: title

        this.itemId = itemId;

        addElement(new ElementLabel(ShopHelper.buildDescription(itemId))); // 0
        addElement(new ElementSlider(Message.WDW_BUY_AMOUNT.getText(), 0, maxAmount, 1, 1)); // 1
    }

    @Override
    public void update() {}

    public void onResponse(PlayerFormRespondedEvent event) {
        /*int amount = (int)getResponse().getSliderResponse(1);

        if (amount == 0) { // Back
            getScreenManager().back();
            return;
        }

        Buy.buy(event.getPlayer(), getScreenManager(), amount, itemId);*/
    }

}