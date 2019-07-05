package ua.leonidius.trdinterface.buy;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.ShopHelper;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public class BuySelectAmountScreen extends FormWindowCustom implements Screen {

    private int shopId, categoryId, itemId;

    public BuySelectAmountScreen(Player player, int shopId, int categoryId, int itemId, int maxAmount) {
        //super(Message.WDW_BUY_TITLE.getText(name));
        super("");

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        try {
            addElement(new ElementLabel(ShopHelper.buildDescription(itemId))); // 0
            addElement(new ElementSlider(Message.WDW_BUY_AMOUNT.getText(), 0, maxAmount, 1, 1)); // 1
        } catch (SQLException | IOException e) {
            Trading.getPlugin().getLogger().error(e.getMessage());
            player.showFormWindow(new BuyFailScreen(3, shopId, categoryId));
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int amount = (int)getResponse().getSliderResponse(1);

        if (amount == 0) {
            player.showFormWindow(new BuyItemSelectorScreen(shopId, categoryId, player.hasPermission("shop.edit")));
        }

        Buy.buy(player, amount, shopId, categoryId, itemId);
    }

}