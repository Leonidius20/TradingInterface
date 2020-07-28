package ua.leonidius.trdinterface.views.screens.buy.items.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.discounts.AddDiscountController;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

import java.util.LinkedList;

/**
 * Created by Leonidius20 on 10.08.18.
 */
public class AddDiscountScreen extends CustomScreen {

    private final transient AddDiscountController controller;

    /**
     * Multipliers to convert input to seconds
     */
    private final transient int[] multipliers = {
            60, // 1 min = 60 sec
            60 * 60, // 1 h = 60 min
            24 * 60 * 60, // 1 day = 24 h
            30 * 24 * 60 * 60, // 1 month = 30 days
    };

    public AddDiscountScreen(AddDiscountController controller) {
        super(Message.WDW_EDIT_DISCOUNT_TITLE.getText());

        this.controller = controller;

        LinkedList<String> durationMeasures = new LinkedList<>();
        // TODO: translate
        durationMeasures.add("minutes"); // 0
        durationMeasures.add("hours");   // 1
        durationMeasures.add("days");    // 2
        durationMeasures.add("months");  // 3

        // Discount percentage, index 0
        addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_HINT.getText()));

        // Discount duration, index 1
        addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_DURATION.getText()));

        // Discount duration unit of measure, index 2
        addElement(new ElementDropdown("", durationMeasures));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        String percentS = getResponse().getInputResponse(0);
        String durationS = getResponse().getInputResponse(1);
        int multiplierIndex = getResponse().getDropdownResponse(2).getElementID();
        int multiplier = multipliers[multiplierIndex];
        controller.addDiscount(percentS, durationS, multiplier);
    }

    @Override
    public void update() {
    }

}