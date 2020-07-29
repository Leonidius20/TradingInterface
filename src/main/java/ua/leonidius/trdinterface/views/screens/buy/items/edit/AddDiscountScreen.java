package ua.leonidius.trdinterface.views.screens.buy.items.edit;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.buy.discounts.AddDiscountController;
import ua.leonidius.trdinterface.views.screens.CustomScreen;

import java.util.LinkedList;

/**
 * Represents the screen for adding discounts to buyable items.
 *
 * @author Leonidius20
 */
public class AddDiscountScreen extends CustomScreen {

    private final transient AddDiscountController controller;

    public AddDiscountScreen(AddDiscountController controller) {
        super(Message.WDW_EDIT_DISCOUNT_TITLE.getText());

        this.controller = controller;

        LinkedList<String> durationUnits = new LinkedList<>();
        for (AddDiscountController.TimeUnit unit : controller.getTimeUnits()) {
            durationUnits.add(unit.getName());
        }

        // Discount percentage, index 0
        addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_HINT.getText()));

        // Discount duration, index 1
        addElement(new ElementInput(Message.WDW_EDIT_DISCOUNT_DURATION.getText()));

        // Discount duration unit of measure, index 2
        addElement(new ElementDropdown("", durationUnits));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        String percentS = getResponse().getInputResponse(0);
        String durationS = getResponse().getInputResponse(1);
        int unitIndex = getResponse().getDropdownResponse(2).getElementID();
        AddDiscountController.TimeUnit unit =
                AddDiscountController.TimeUnit.values()[unitIndex];
        controller.addDiscount(percentS, durationS, unit);
    }

    @Override
    public void update() {}

}