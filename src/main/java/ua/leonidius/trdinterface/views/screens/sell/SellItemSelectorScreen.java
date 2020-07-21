/*package ua.leonidius.trdinterface.controllers.sell;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.views.elements.SellItemButton;
import ua.leonidius.trdinterface.views.screens.MainScreen;
import ua.leonidius.trdinterface.views.screens.Screen;

import static ua.leonidius.trdinterface.Trading.sellCfg;

/**
 * Created by Leonidius20 on 07.07.18.
 */
/*public class SellItemSelectorScreen extends FormWindowSimple implements Screen {

    public SellItemSelectorScreen(PlayerInventory inventory) {
        super(Message.WDW_SELL_SELECT_ITEM.getText(), "");

        addButton(new ElementButton(Message.BTN_BACK.getText()));

        for (String key : sellCfg.getKeys()) {
            String id = key.replace("-", ":");
            Item item = Item.fromString(id);
            if (inventory.contains(item)) {
                addButton(new SellItemButton(id, Sell.getItemCount(inventory, item)));
            }
        }

        // If there is only back button
        if (getButtons().size() == 1) {
            setContent(Message.WDW_SELL_NOTHING.getText());
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        // Back button pressed
        if (!(getResponse().getClickedButton() instanceof SellItemButton)) {
            event.getPlayer().showFormWindow(new MainScreen(1));
            return;
        }

        SellItemButton button = (SellItemButton)getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new SellScreen(button.getStringId(), button.getAmount()));

    }
}*/
