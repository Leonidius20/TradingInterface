package ua.leonidius.trdinterface.views.screens.buy.items.edit.enchantment;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.item.enchantment.Enchantment;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.AddEnchantmentController;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

public class AddEnchantmentScreen extends CustomScreen {

    private transient final AddEnchantmentController controller;

    public AddEnchantmentScreen(AddEnchantmentController controller) {
        super(Message.WDW_ADD_ENCHANTMENT_TITLE.getText());

        this.controller = controller;

        ElementDropdown dropdown = new ElementDropdown(Message.WDW_ADD_ENCHANTMENT_SELECTOR_HINT.getText());

        for (Enchantment enchantment : Enchantment.getEnchantments()) {
            dropdown.addOption(enchantment.getName());
        }

        addElement(dropdown); // 0

        addElement(new ElementSlider(Message.WDW_ADD_ENCHANTMENT_LEVEL_HINT.getText(),
                0, 5, 1, 0)); // 1
    }

    @Override
    public void update() {}

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int enchantmentLevel = (int) getResponse().getSliderResponse(1);

        if (enchantmentLevel == 0) { // Going back
            controller.back();
            return;
        }

        int enchantmentId = getResponse().getDropdownResponse(0).getElementID();

        controller.selectEnchantment(enchantmentId, enchantmentLevel);
    }

}