package ua.leonidius.trdinterface.buy.edit.items;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.buy.BuyManageItemScreen;
import ua.leonidius.trdinterface.screens.Screen;

public class AddEnchantmentFailScreen extends FormWindowSimple implements Screen {

    private int shopId, categoryId, itemId;

    public AddEnchantmentFailScreen(int shopId, int categoryId, int itemId) {
        super(Message.WDW_FAIL_TITLE.getText(), Message.WDW_ADD_ENCHANTMENT_FAIL.getText());

        this.shopId = shopId;
        this.categoryId = categoryId;
        this.itemId = itemId;

        addButton(new ElementButton(Message.BTN_BACK.getText()));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        player.showFormWindow(new BuyManageItemScreen(player, shopId, categoryId, itemId));
    }

}