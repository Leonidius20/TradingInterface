package ua.leonidius.trdinterface.views.screens;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import ua.leonidius.trdinterface.views.ScreenManager;

/**
 * Created by Leonidius20 on 04.07.18.
 */
public interface Screen {

    void onResponse(PlayerFormRespondedEvent event);

    //void onClose(PlayerFormRespondedEvent event);

    ScreenManager getScreenManager();

    void setScreenManager(ScreenManager manager);

    void setIgnoreStack(boolean ignoresStack);

    boolean ignoresStack();

    void update() throws Exception;

}