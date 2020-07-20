package ua.leonidius.trdinterface;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import ua.leonidius.trdinterface.controllers.MainController;
import ua.leonidius.trdinterface.views.ScreenManager;

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

        new MainController(new ScreenManager(player), 1).showScreen();

        return true;
    }

}