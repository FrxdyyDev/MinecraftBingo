package de.frxdy.bingo.Utils.timer;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import de.frxdy.bingo.Utils.WinChecker;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Timer {

    private static int time;

    private static boolean running = false;

    private static int taskID = 0;

    public static void updateTimer() {
        time = BingoSettings.getTime();
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bingoplugin.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if (running) {
                    time--;
                    int hours = time / 3600 % 60;
                    int minutes = time / 60 % 60;
                    int seconds = time % 60;

                    if (time <= 0) {
                        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.CLASSIC_BINGO)) {
                            BingoTeam winner = Bingoplugin.getTeams().get(0);
                            for (BingoTeam team : Bingoplugin.getTeams()) {
                                if (team.getNeededItems().size() < winner.getNeededItems().size()) {
                                    winner = team;
                                }
                            }
                            WinChecker.endGame(winner);
                        }else {
                            BingoTeam winner = Bingoplugin.getTeams().get(0);
                            for (BingoTeam team : Bingoplugin.getTeams()) {
                                if (team.getCollectedItemsCount() > winner.getCollectedItemsCount()) {
                                    winner = team;
                                }
                            }
                            WinChecker.endGame(winner);
                        }
                        Bukkit.getScheduler().cancelTask(taskID);
                    }

                    StringBuilder message = new StringBuilder(ChatColor.GOLD + "" + ChatColor.BOLD);
                    message.append(String.format("%02d", hours)).append(":");
                    message.append(String.format("%02d", minutes)).append(":");
                    message.append(String.format("%02d", seconds));

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message.toString()));
                    }
                }else {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + "" + ChatColor.ITALIC + "The game is paused..."));
                    }
                }
            }
        }, 0, 20L);
    }

    public static void resume() {
        if (!running) {
            running = true;
        }
    }

    public static void pause() {
        if (running) {
            running = false;
        }
    }

    public static void reset() {
        time = BingoSettings.getTime();
        if (running) {
            running = false;
        }
    }

}
