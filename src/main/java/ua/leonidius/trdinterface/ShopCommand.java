package ua.leonidius.trdinterface;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import ua.leonidius.trdinterface.screens.MainScreen;

/**
 * Created by Leonidius20 on 07.01.18.
 */
public class ShopCommand extends PluginCommand implements CommandExecutor{

    @SuppressWarnings("unchecked")
    ShopCommand(Plugin owner) {
        super("shop", owner);
        setExecutor(this);
        setDescription(Message.CMD_SHOP.getText());
        commandParameters.clear();
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            Message.CMD_CONSOLE.print(sender);
            return true;
        }
        player.showFormWindow(new MainScreen(player.hasPermission("shop.edit")));
        return true;
    }
}
