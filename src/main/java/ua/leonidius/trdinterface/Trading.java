package ua.leonidius.trdinterface;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.j256.ormlite.support.ConnectionSource;
import ru.nukkit.dblib.DbLib;
import ua.leonidius.trdinterface.screens.Screen;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Leonidius20 on 07.01.18.
 */
public class Trading extends PluginBase implements Listener {

    public static Config sellCfg;
    private static Trading plugin;
    public static Settings settings;

    private static Connection connection;

    @Override
    public void onEnable() {
        plugin = this;

        settings = new Settings(this);
        settings.load();

        Message.init(this);

        getServer().getCommandMap().register("trd-interface", new ShopCommand(this));

        getServer().getPluginManager().registerEvents(this, this);

        getDataFolder().mkdirs();
        /*imageFolder = new File(getDataFolder(), "images");
        imageFolder.mkdirs();*/

        saveResource("config.yml");

        sellCfg = new Config(new File(getDataFolder(), "sellList.yml"));

        //connection = DbLib.getSQLiteConnection(new File(getDataFolder(), "shops.db"));

        // Getting ORMLite connection
        ConnectionSource source = DbLib.getConnectionSourceSQLite("shops.db");
        // maybe it should be SQL and not SQLite?

        try {
            Statement createDb = connection.createStatement();
            String createShopsTable = "CREATE TABLE IF NOT EXISTS shops" +
                    "(record_id INTEGER PRIMARY KEY," +
                    "name TEXT NOT NULL UNIQUE)";
            String createDefaultShop = "INSERT OR IGNORE INTO shops(record_id, name) VALUES(1, 'default')";
            String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories" +
                    "(record_id INTEGER PRIMARY KEY," +
                    "shop_id INTEGER NOT NULL," +
                    "name TEXT NOT NULL UNIQUE," +
                    "FOREIGN KEY(shop_id) REFERENCES shops(record_id)," +
                    "UNIQUE(shop_id, name))";
            String createBuyItemsTable = "CREATE TABLE IF NOT EXISTS buy_items" +
                    "(record_id INTEGER PRIMARY KEY," +
                    "shop_id INTEGER NOT NULL," +
                    "category_id INTEGER NOT NULL," +
                    "id TEXT NOT NULL," +
                    "price REAL NOT NULL," +
                    "nbt BLOB," +
                    "FOREIGN KEY(shop_id) REFERENCES shops(record_id)," +
                    "FOREIGN KEY(category_id) REFERENCES categories(record_id))";
            String createSellItemsTable = "CREATE TABLE IF NOT EXISTS sell_items" +
                    "(record_id INTEGER PRIMARY KEY," +
                    "shop_id INTEGER NOT NULL," +
                    "id TEXT NOT NULL," +
                    "price REAL NOT NULL," +
                    "nbt BLOB," +
                    "FOREIGN KEY(shop_id) REFERENCES shops(record_id))";
            createDb.executeUpdate(createShopsTable);
            createDb.executeUpdate(createDefaultShop);
            createDb.executeUpdate(createCategoriesTable);
            createDb.executeUpdate(createBuyItemsTable);
            createDb.executeUpdate(createSellItemsTable);
            createDb.close();
        } catch (SQLException e) {
            getLogger().critical(e.getMessage());
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            connection.close();
        } catch (SQLException e) {
            getLogger().critical(e.getMessage());
        }
    }

    // Form response handler
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        if (!(event.getWindow() instanceof Screen)) return;
        ((Screen)event.getWindow()).onResponse(event);
    }

    public static Trading getPlugin(){
        return plugin;
    }

    public static Connection getDbConnection() {
        return connection;
    }

}