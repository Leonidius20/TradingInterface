package ua.leonidius.trdinterface.elements;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.ItemName;
import ua.leonidius.trdinterface.Message;

/**
 * Created by Leonidius20 on 07.07.18.
 */
public class SellItemButton extends ElementButton {

    private String id;
    private int amount;

    public SellItemButton(String id, int amount) {
        super(Message.BTN_ITEM_SELL.getText(ItemName.get(id), amount));
        this.id = id;
        this.amount = amount;
    }

    public String getStringId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
