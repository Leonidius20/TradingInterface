package ua.leonidius.trdinterface;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ru.nukkit.dblib.DbLib;
import ua.leonidius.trdinterface.models.*;
import ua.leonidius.trdinterface.utils.YamlToDbConverter;
import ua.leonidius.trdinterface.views.screens.Screen;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.SQLException;

/**
 * Created by Leonidius20 on 07.01.18.
 */
public class Trading extends PluginBase implements Listener {

    private static Trading plugin;
    private static Settings settings;

    private static ConnectionSource source;

    @Override
    public void onEnable() {
        plugin = this;

        settings = new Settings(this);
        settings.load();

        Message.init(this);

        getServer().getCommandMap().register("trd-interface", new ShopCommand(this));

        getServer().getPluginManager().registerEvents(this, this);

        getDataFolder().mkdirs();

        /* imageFolder = new File(getDataFolder(), "images");
        imageFolder.mkdirs(); */

        saveResource("config.yml");

        source = DbLib.getConnectionSource(
                DbLib.getSqliteUrl(new File(getDataFolder(), "shops.db")),
                null, null);

        try {
            TableUtils.createTableIfNotExists(source, Shop.class);

            // Creating the default shop (accessed with /shop)
            Dao<Shop, Integer> shopDao = DaoManager.createDao(source, Shop.class);
            shopDao.createIfNotExists(Shop.getDefault());

            TableUtils.createTableIfNotExists(source, Category.class);
            TableUtils.createTableIfNotExists(source, BuyableItem.class);
            TableUtils.createTableIfNotExists(source, SellableItem.class);
            TableUtils.createTableIfNotExists(source, Discount.class);
            TableUtils.createTableIfNotExists(source, Translation.class);
        } catch (SQLException e) {
            getLogger().critical(e.getMessage());
            getPluginLoader().disablePlugin(this);
        }

        // Converting old configs
        File buyableItemsFile = new File(getDataFolder(), "buyList.yml");
        if (buyableItemsFile.exists()) {
            Message.LOG_BUYABLE_ITEMS_FOUND.log();
            Config buyableItems = new Config(buyableItemsFile);
            if (YamlToDbConverter.convertBuyableItems(buyableItems)) {
                buyableItemsFile.delete();
            }
        }

        File sellableItemsFile = new File(getDataFolder(), "sellList.yml");
        if (sellableItemsFile.exists()) {
            Message.LOG_SELLABLE_ITEMS_FOUND.log();
            Config sellableItems = new Config(sellableItemsFile);
            if (YamlToDbConverter.convertSellableItems(sellableItems)) {
                sellableItemsFile.delete();
            }
        }

        File translationsFile = new File(getDataFolder(), "customItemNames.yml");
        if (translationsFile.exists()) {
            Message.LOG_TRANSLATIONS_FOUND.log();
            Config translations = new Config(translationsFile);
            if (YamlToDbConverter.convertTranslations(translations)) {
                translationsFile.delete();
            }
        }
    }

    @Override
    public void onDisable() {
        source.closeQuietly();
    }

    // Form response handler
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (!(event.getWindow() instanceof Screen)) return;
        if (event.getResponse() == null) {
            return;
        }
        ((Screen) event.getWindow()).onResponse(event);

        // When we show a window object, which was already shown before,
        // from stack (see ScreenManager.back()), that window object
        // already has its 'response' field set from the previous
        // interaction with it. Because of that, when that 'reused'
        // window gets closed with the 'x' button, the 'response' field
        // is equal to the response from the previous interaction, which
        // is treated as if the button pressed during the previous
        // interaction was pressed again (i.e. an unrequested window is
        // being shown, instead of the whole interface closing). We need
        // to set 'response' to null so that if this windows gets reused,
        // it is treated as a "fresh" window. event.getWindow().setResponse(null)
        // results in a NullPointerException, so we use reflection instead.
        try {
            Class<?> subclass;
            if (event.getWindow() instanceof FormWindowSimple)
                subclass = FormWindowSimple.class;
            else if (event.getWindow() instanceof FormWindowModal)
                subclass = FormWindowModal.class;
            else if (event.getWindow() instanceof FormWindowCustom)
                subclass = FormWindowCustom.class;
            else return;

            Field response = subclass.getDeclaredField("response");
            boolean accessible = response.isAccessible();
            response.setAccessible(true);
            response.set(event.getWindow(), null);
            response.setAccessible(accessible);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Trading.printException(e);
        }
    }

    public static Trading getPlugin() {
        return plugin;
    }

    public static ConnectionSource getSource() {
        return source;
    }

    public static Settings getSettings() {
        return settings;
    }

    /**
     * Prints exception details to the console.
     *
     * @param e exception to handle
     */
    public static void printException(Exception e) {
        getPlugin().getLogger().error(e.getMessage());
        e.printStackTrace();
    }

}