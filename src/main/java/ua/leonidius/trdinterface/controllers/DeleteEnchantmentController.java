package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.models.BuyableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ModalScreen;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteEnchantmentController extends ModalController {

    private final ShopItem item;
    private final Enchantment enchantment;

    public DeleteEnchantmentController(ScreenManager manager,
                                       ShopItem item, Enchantment enchantment) {
        super(manager);
        this.item = item;
        this.enchantment = enchantment;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new ModalScreen(Message.WDW_REMOVE_ENCHANTMENT_TITLE.getText(),
                Message.WDW_REMOVE_ENCHANTMENT_CONF.getText(getName(), item.getName()),
                this), true);
    }

    @Override
    public void onPositiveResponse() {
        Item oldGameItem = item.toGameItem();
        Item newGameItem = Item.fromString(item.getItemId());
        if (!oldGameItem.getCustomName().equals(""))
            newGameItem.setCustomName(oldGameItem.getCustomName());
        if (oldGameItem.getLore() != null && oldGameItem.getLore().length != 0) {
            newGameItem.setLore(oldGameItem.getLore());
        }

        for (Enchantment e : oldGameItem.getEnchantments()) {
            if (e.getId() != enchantment.getId()) {
                newGameItem.addEnchantment(e);
            }
        }

        try {
            byte[] newNbtBytes = newGameItem.getNamedTag() == null
                    ? null : NBTIO.write(newGameItem.getNamedTag());
            item.setNbt(newNbtBytes);
            item.update();
        } catch (IOException | SQLException e) {
            handleException(e);
            return;
        }

        if (Trading.getSettings().logEdits()) {
            if (item instanceof BuyableItem) {
                Message.LOG_BUY_ENCHANTMENT_REMOVED.log(manager.getPlayer().getName(),
                        enchantment.getName(), enchantment.getLevel(),
                        item.getName(), item.getItemId());
            } else {
                Message.LOG_SELL_ENCHANTMENT_REMOVED.log(manager.getPlayer().getName(),
                        enchantment.getName(), enchantment.getLevel(),
                        item.getName(), item.getItemId());
            }
        }

        manager.back();
    }

    /**
     * @return a string with the name and the level of the enchantment
     */
    private String getName() {
        return enchantment.getName() + " " + enchantment.getLevel();
    }

}
