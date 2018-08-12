package ua.leonidius.trdinterface;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.SimpleConfig;
import me.onebone.economyapi.EconomyAPI;

/**
 * Created by Leonidius20 on 05.01.18.
 */
public class Settings extends SimpleConfig {

    public Settings(Plugin plugin) {
        super(plugin);
        currency = EconomyAPI.getInstance().getMonetaryUnit();
    }

    //general
    @Path (value = "save-translation")
    public boolean save_translation = false;
    @Path (value = "language")
    public String language = "default";
    @Path (value = "edit-logging")
    public boolean editLogging = true;
    @Path (value = "transaction-logging")
    public boolean transactionLogging = false;

    public String currency;

}
