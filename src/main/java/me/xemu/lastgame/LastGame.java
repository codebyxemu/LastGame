/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.lastgame;

import me.xemu.lastgame.commands.TagCommand;
import me.xemu.lastgame.events.TaggedEvent;
import me.xemu.lastgame.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LastGame extends JavaPlugin
{

    private static LastGame instance;
    public Game game;

    public static String prefix = "&8┃ &5&lLast&d&lGame  &r";

    @Override public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(new TaggedEvent(), this);
        getCommand("last").setExecutor(new TagCommand());

        game = new Game();
        instance = this;
        getLogger().info("Plugin Enabled");
    };

    @Override public void onDisable()
    {
        instance = null;
        getLogger().info("Plugin Disabled");

        if(game.isPlaying())
        {
            game.end();
        };
    };

    public static LastGame getInstance()
    {
        return instance;
    };


};