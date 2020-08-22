package ua.leonidius.trdinterface.controllers;

import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.ListScreen;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

public class ManageEnchantmentsController extends ListController<Enchantment> {

    private final ShopItem item;

    public ManageEnchantmentsController(ScreenManager manager, ShopItem item) {
        super(manager);
        this.item = item;
    }

    @Override
    public void showScreen() {
        LinkedHashMap<String, ListScreen.ButtonCallback> buttons =
                new LinkedHashMap<>();

        // TODO: check divided permissions
        if (manager.getPlayer().hasPermission("shop.edit")) {
            buttons.put(Message.BTN_ADD_ENCHANTMENT.getText(), this::addEnchantment);
        }

        manager.addAndShow(new ListScreen<>(this,
                Message.WDW_MANAGE_ENCHANTMENTS_EMPTY.getText(),
                Message.WDW_MANAGE_ENCHANTMENTS_TEXT.getText(), buttons));
    }

    @Override
    public Collection<Enchantment> fetchItems() {
        return Arrays.asList(item.toGameItem().getEnchantments());
    }

    @Override
    public void selectItem(Enchantment e) {
        new DeleteEnchantmentController(manager, item, e).showScreen();
    }

    private void addEnchantment() {
        new AddEnchantmentController(manager, item).showScreen();
    }

    @Override
    public String buildItemButtonText(Enchantment enchantment) {
        return enchantment.getName() + " " + enchantment.getLevel();
    }

    @Override
    public String getTitle() {
        return Message.WDW_MANAGE_ENCHANTMENTS_TITLE.getText(item.getName());
    }

}
