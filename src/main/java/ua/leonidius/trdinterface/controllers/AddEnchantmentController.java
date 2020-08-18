package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.buy.items.edit.enchantment.AddEnchantmentScreen;

import java.io.IOException;
import java.sql.SQLException;

public class AddEnchantmentController extends BaseController {

    private final ShopItem item;

    public AddEnchantmentController(ScreenManager manager, ShopItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AddEnchantmentScreen(this), true);
    }

    public void selectEnchantment(int id, int level) {
        Item gameItem = item.toGameItem();
        gameItem.addEnchantment(Enchantment.get(id).setLevel(level));

        try {
            byte[] newNbtBytes = NBTIO.write(gameItem.getNamedTag());
            item.setNbt(newNbtBytes);
            item.update();
        } catch (IOException | SQLException e) {
            handleException(e);
            return;
        }

        if (Trading.getSettings().logEdits()) {
            if (item instanceof BuyableItem) {
                Message.LOG_BUY_ENCHANTMENT_ADDED.log(manager.getPlayer().getName(),
                        Enchantment.get(id).getName(), level,
                        gameItem.getName(), item.getItemId());
            } else {
                Message.LOG_SELL_ENCHANTMENT_ADDED.log(manager.getPlayer().getName(),
                        Enchantment.get(id).getName(), level,
                        gameItem.getName(), item.getItemId());
            }
        }

        manager.back();
    }

}

