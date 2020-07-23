package ua.leonidius.trdinterface;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import ua.leonidius.trdinterface.controllers.MainController;
import ua.leonidius.trdinterface.models.Shop;
import ua.leonidius.trdinterface.views.ScreenManager;

import java.sql.SQLException;

/**
 * Created by Leonidius20 on 07.01.18.
 */
public class ShopCommand extends PluginCommand<Trading> implements CommandExecutor {

    ShopCommand(Trading owner) {
        super("shop", owner);
        setExecutor(this);
        setDescription(Message.CMD_SHOP.getText());
        getCommandParameters().clear();
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isPlayer()) {
            Message.CMD_CONSOLE.print(sender);
            return true;
        }

        Player player = sender.getServer().getPlayer(sender.getName());
        if (player == null) return false;

        try {
            Dao<Shop, Integer> shopDao = DaoManager.createDao(Trading.getSource(), Shop.class);
            Shop shop = shopDao.queryForId(1); // default shop has record_id = 1
            new MainController(new ScreenManager(player), shop).showScreen();
        } catch (SQLException e) {
            // TODO: show an error screen
            Trading.getPlugin().getLogger().debug(e.getMessage());
            if (Trading.settings.debugMode) e.printStackTrace();
        }

        return true;
    }

}