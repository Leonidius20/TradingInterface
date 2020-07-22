package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowModal;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.controllers.ModalController;

public class ModalScreen extends FormWindowModal implements Screen {

    private transient boolean ignoresStack = true;
    private transient final ModalController controller;

    public ModalScreen(String title, String content, ModalController controller) {
        super(title, content, Message.BTN_YES.getText(), Message.BTN_NO.getText());
        this.controller = controller;
    }

    @Override
    public void setIgnoreStack(boolean ignoresStack) {
        this.ignoresStack = ignoresStack;
    }

    @Override
    public boolean ignoresStack() {
        return ignoresStack;
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        if (getResponse().getClickedButtonId() == 0) { // Positive button clicked
            controller.onPositiveResponse();
        } else controller.onNegativeResponse();
    }

    @Override
    public void update() {}

}