package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.AmountSelectorController;

public class AmountSelectorScreen extends CustomScreen {

    private final transient AmountSelectorController controller;

    public AmountSelectorScreen(AmountSelectorController controller,
                                String title, String content,
                                int maxAmount) {
        super(title);

        this.controller = controller;

        addElement(new ElementLabel(content));
        addElement(new ElementLabel(Message.WDW_SELECT_ZERO_TO_RETURN.getText()));
        addElement(new ElementSlider(Message.WDW_BUY_AMOUNT.getText(), 0, maxAmount, 1, 1));
    }

    @Override
    public void update() {}

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int amount = (int)getResponse().getSliderResponse(2);
        if (amount == 0) controller.back();
        else controller.selectAmount(amount);
    }

}
