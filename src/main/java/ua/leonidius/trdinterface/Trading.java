package ua.leonidius.trdinterface;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.File;

/**
 * Created by Leonidius20 on 07.01.18.
 */
public class Trading extends PluginBase implements Listener {

    public static Config buyCfg, sellCfg, customNames;
    public static Trading plugin;
    public static Settings settings;

    @Override
    public void onEnable() {
        plugin = this;

        settings = new Settings(this);
        settings.load();

        Message.init(this);

        getServer().getCommandMap().register("trd-interface", new ShopCommand(this));

        getServer().getPluginManager().registerEvents(this, this);

        getDataFolder().mkdirs();
        saveResource("config.yml");
        saveResource("buyList.yml");
        saveResource("sellList.yml");
        saveResource("customItemNames.yml");

        buyCfg = new Config(new File(getDataFolder(), "buyList.yml"));
        sellCfg = new Config(new File(getDataFolder(), "sellList.yml"));
        customNames = new Config(new File(getDataFolder(), "customItemNames.yml"));
    }

    public static Trading getPlugin(){
        return plugin;
    }

    // Form response handler
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        Screen screen = (Screen)event.getWindow();
        screen.onResponse(event);
    }
}
