/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.lastgame.commands;

import me.xemu.lastgame.LastGame;
import me.xemu.lastgame.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor
{

    @Override public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        if(sender instanceof Player)
        {
            final Player player = (Player) sender;

            switch (args.length)
            {
                case 1:
                    if(args[0].equalsIgnoreCase("start"))
                    {
                        LastGame.getInstance().game.tagged(LastGame.getInstance().game.pickFirstIt());

                        player.sendMessage(LastGame.prefix + "&aGame Started! &7Enjoy!");

                        return true;
                    } else if (args[0].equalsIgnoreCase("end")) {
                        LastGame.getInstance().game.end();

                        player.sendMessage(LastGame.prefix + "&cGame Ended! &7Well played!");

                        return true;
                    };
                default:
                    player.sendMessage(Utils.translate(LastGame.prefix + "&cWrong usage: /last <start/end>"));

            };
            return true;
        } else {
            sender.sendMessage(Utils.translate("Only players can do this."));
        };
        return true;
    };
};