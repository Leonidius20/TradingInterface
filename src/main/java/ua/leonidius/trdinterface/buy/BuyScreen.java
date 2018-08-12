package ua.leonidius.trdinterface.buy;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.buy.Buy;
import ua.leonidius.trdinterface.screens.Screen;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public class BuyScreen extends FormWindowCustom implements Screen {

    private String id, categoryId;

    public BuyScreen(String categoryId, String id, double price, int maxAmount) {
        super(Message.WDW_BUY_TITLE.getText(ItemName.get(id)));
        this.id = id;
        this.categoryId = categoryId;

        addElement(new ElementLabel(Message.WDW_BUY_PRICE.getText(price, Trading.settings.currency)));
        addElement(new ElementSlider(Message.WDW_BUY_AMOUNT.getText(), 1, maxAmount, 1, 1));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int amount = (int)getResponse().getSliderResponse(1);
        Item item = Item.fromString(id);
        item.setCount(amount);
        Buy.buy(event.getPlayer(), categoryId, id, amount);
    }

}
