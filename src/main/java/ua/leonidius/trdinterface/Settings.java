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
    }

    @Override
    public boolean load() {
        boolean result = super.load();
        currency = EconomyAPI.getInstance().getMonetaryUnit();
        return result;
    }

    @Path(value = "save-translation")
    private boolean saveTranslation = false;

    @Path(value = "language")
    private String language = "default";

    @Path(value = "edit-logging")
    private boolean editLogging = true;

    @Path(value = "transaction-logging")
    private boolean transactionLogging = false;

    @Path(value = "debug")
    private boolean debugMode = false;

    private String currency;

    public boolean saveLanguageFile() {
        return saveTranslation;
    }

    public String getLanguage() {
        return language;
    }

    public boolean logEdits() {
        return editLogging;
    }

    public boolean logTransactions() {
        return transactionLogging;
    }

    public boolean debugActive() {
        return debugMode;
    }

    public String getCurrency() {
        return currency;
    }

}