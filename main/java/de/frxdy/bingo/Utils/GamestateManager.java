package de.frxdy.bingo.Utils;

import de.frxdy.bingo.BingoEvents.EventBingo;
import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import de.frxdy.bingo.Utils.timer.Timer;
import de.frxdy.bingo.forceItem.ForceItem;
import de.frxdy.bingo.scoreboard.ScoreboardHandler;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GamestateManager {

    public enum Gamestate{
        WAITING,
        PLAYING,
        STARTING,
        PAUSED
    }

    public enum BingoMode {
        FORCE_ITEM,
        CLASSIC_BINGO
    }

    public static BingoMode gamemode = BingoMode.CLASSIC_BINGO;

    public static Gamestate gamestate = Gamestate.WAITING;

    public static void startBingo2() {
        gamestate = Gamestate.STARTING;
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(11);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.getLevel() > 1) {
                        all.setLevel(all.getLevel()-1);
                    }else {
                        gamestate = Gamestate.PLAYING;
                        all.setLevel(0);
                        all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
                        Timer.resume();
                        for (BingoTeam team : Bingoplugin.getTeams()) {
                            for (Player player : team.getPlayers()) {
                                player.setFlying(false);
                                player.setGameMode(GameMode.SURVIVAL);
                                player.getInventory().clear();
                                player.teleport(team.getSpawnLocation());
                            }
                        }
                        if (BingoSettings.isEventBingoActive()) {
                            EventBingo.startRandomEvent();
                        }
                        cancel();
                    }
                    if (all.getLevel() <= 5 && all.getLevel() > 0) {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    }
                }
            }
        }.runTaskTimer(Bingoplugin.getPlugin(), 0, 20L);
    }

    public static void startBingo() {
        GamestateManager.gamestate = GamestateManager.Gamestate.STARTING;
        final int[] countDown = {11};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (countDown[0] >= 1) {
                    countDown[0]--;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(countDown[0]);
                    }
                }
                if (countDown[0] <= 5 && countDown[0] > 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    }
                }else if (countDown[0] == 0) {
                    GamestateManager.gamestate = GamestateManager.Gamestate.PLAYING;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
                    }
                    Timer.resume();
                    for (BingoTeam team : Bingoplugin.getTeams()) {
                        for (Player player : team.getPlayers()) {
                            player.setFlying(false);
                            player.setGameMode(GameMode.SURVIVAL);
                            player.getInventory().clear();
                            player.teleport(team.getSpawnLocation());
                        }
                    }
                    if (BingoSettings.isEventBingoActive()) {
                        EventBingo.startRandomEvent();
                    }
                    cancel();
                }
            }
        }.runTaskTimer(Bingoplugin.getPlugin(), 0, 20L);
    }

    public static void startForceItem() {
        GamestateManager.gamestate = GamestateManager.Gamestate.STARTING;
        final int[] countDown = {11};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (countDown[0] >= 1) {
                    countDown[0]--;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(countDown[0]);
                    }
                }
                if (countDown[0] <= 5 && countDown[0] > 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                    }
                }else if (countDown[0] == 0) {
                    GamestateManager.gamestate = GamestateManager.Gamestate.PLAYING;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);
                    }
                    Timer.resume();
                    ForceItem.generateNewItem();
                    ForceItem.setBar(Bukkit.createBossBar(ChatColor.GOLD + "Searched Item: " + ChatColor.GREEN + Bingoplugin.getTeams().get(0).getNeededItems().get(0), BarColor.GREEN, BarStyle.SOLID));
                    for (BingoTeam team : Bingoplugin.getTeams()) {
                        for (Player player : team.getPlayers()) {
                            player.setFlying(false);
                            player.setGameMode(GameMode.SURVIVAL);
                            player.getInventory().clear();
                            player.teleport(team.getSpawnLocation());
                            ForceItem.getBar().addPlayer(player);
                        }
                    }
                    cancel();
                    Scoreboard scoreboard = ScoreboardHandler.updateScoreboard();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setScoreboard(scoreboard);
                    }
                }
            }
        }.runTaskTimer(Bingoplugin.getPlugin(), 0, 20L);
    }

    public static void resetBingo() {
        Timer.reset();
        gamestate = Gamestate.WAITING;
        WorldReset.resetWorlds();
    }

}