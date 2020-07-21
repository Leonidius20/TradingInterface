package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import ua.leonidius.trdinterface.controllers.NamingController;

/**
 * This is a generic screen for naming things
 * (i.e. naming a new category, renaming an existing one etc.)
 */
public class NamingScreen extends CustomScreen {

    private transient final NamingController controller;

    public NamingScreen(NamingController controller, String title, String text) {
        super(title);
        this.controller = controller;
        addElement(new ElementInput(text));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        // TODO: possibly replace with a callback element
        String name = getResponse().getInputResponse(0);
        if (name.isEmpty()) controller.back();
        else controller.giveName(name);
    }

    @Override
    public void update() {}

}
