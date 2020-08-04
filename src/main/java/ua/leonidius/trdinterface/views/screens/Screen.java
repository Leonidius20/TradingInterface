package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public interface Screen {

    void onResponse(PlayerFormRespondedEvent event);

    //void onClose(PlayerFormRespondedEvent event);

    void setIgnoreStack(boolean ignoresStack);

    boolean ignoresStack();

    /**
     * Called before showing the screen for the first time
     * and when returning to the screen using a back button
     */
    void update();

}