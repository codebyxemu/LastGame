/*
Plugin Developed & Maintained by Xemu
 */
package me.xemu.lastgame.game;

import me.xemu.lastgame.LastGame;
import me.xemu.lastgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Game
{

    private int task;
    private Player it;
    private boolean isPlaying;

    public Game()
    {
        this.task = -1;
        this.isPlaying = false;
    };

    public int getTask()
    {
        return task;
    };

    public void setTask(int task)
    {
        this.task = task;
    };

    public Player getIt()
    {
        return it;
    };

    public void setIt(Player it)
    {
        this.it = it;
    };

    public boolean isPlaying()
    {
        return isPlaying;
    };

    public void setPlaying(boolean playing)
    {
        isPlaying = playing;
    };

    public Player pickFirstIt()
    {
          return Bukkit.getOnlinePlayers().stream().skip((int)
                  (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);
    };

    public Scoreboard getBoard()
    {
        final ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard board = manager.getNewScoreboard();

        final Objective obj = board.registerNewObjective("tagScoreboard", "dummy",
                Utils.translate("&5&lSeconds left: &d"));
        obj.setDisplayName(String.valueOf(DisplaySlot.SIDEBAR));
        final Score score = obj.getScore(Utils.translate("&5" + it.getName() + " &7is it!"));
        score.setScore(0);
        return board;
    };

    public void end()
    {
       setPlaying(false);

       Bukkit.getScheduler().cancelTask(getTask());

       it.sendMessage(Utils.translate(LastGame.prefix + "&cAww! &7You lost!"));
       it.setGlowing(false);

       for (final Player online : Bukkit.getOnlinePlayers())
       {
            online.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            if(online != it)
            {
                online.sendMessage(Utils.translate(LastGame.prefix + "&aCongratulations! &7You won!"));
            };
       };
    };

    public void tagged(final Player player)
    {
        setPlaying(true);

        if (getTask() != -1)
            Bukkit.getScheduler().cancelTask(task);
        setIt(player);
        it.setGlowing(true);

        for (final Player online : Bukkit.getOnlinePlayers())
        {
              online.setScoreboard(getBoard());
        };

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(LastGame.getInstance(), new Runnable() {

            int timeleft = 300;

            @Override public void run()
            {
                if(timeleft <= 0)
                {
                    end();
                    return;
                };

                for (final Player online : Bukkit.getOnlinePlayers())
                    online.getScoreboard().getObjective(DisplaySlot.SIDEBAR)
                    .setDisplayName(Utils.translate("&5&lSeconds left: &d" + timeleft));

                timeleft--;
            };
        }, 0, 20);
    };

};