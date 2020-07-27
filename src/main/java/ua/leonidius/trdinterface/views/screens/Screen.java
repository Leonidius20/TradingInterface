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

    void update();

}