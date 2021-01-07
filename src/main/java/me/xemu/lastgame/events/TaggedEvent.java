/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.lastgame.events;

import me.xemu.lastgame.LastGame;
import me.xemu.lastgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TaggedEvent implements Listener
{

    @EventHandler public void onTag(final EntityDamageByEntityEvent event)
    {
          if(!LastGame.getInstance().game.isPlaying()) return;

          if(!(event.getEntity() instanceof Player)) return;

          if(!(event.getDamager() instanceof Player)) return;

          event.setCancelled(true);

          if((Player) event.getDamager() != LastGame.getInstance().game.getIt()) return;

          event.getDamager().setGlowing(false);

          ((Player) event.getEntity()).spawnParticle(Particle.FLASH, event.getEntity().getLocation(), 0);

          Bukkit.broadcastMessage(Utils.translate(LastGame.prefix + "&5" + event.getEntity() + " &7is last&7!"));
    };

    @EventHandler public void onPlayerJoin(final PlayerJoinEvent event)
    {
        if(!LastGame.getInstance().game.isPlaying()) return;

        event.getPlayer().setScoreboard(LastGame.getInstance().game.getBoard());
    };

    @EventHandler public void onPlayerQuit(final PlayerQuitEvent event)
    {
        if(!LastGame.getInstance().game.isPlaying()) return;

        if(LastGame.getInstance().game.getIt() == event.getPlayer())
        {
            event.getPlayer().setGlowing(false);
            LastGame.getInstance().game.end();
            Bukkit.broadcastMessage(Utils.translate(LastGame.prefix + "&c" + event.getPlayer().getName() + "&7 left the game. &c&lSore loser!"));
        };
    };

};