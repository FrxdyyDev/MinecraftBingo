package de.frxdy.bingo.BingoEvents;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import de.frxdy.bingo.Utils.WinChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public enum EventBingo {
    NOTHING, RESHUFFLE, DOUBLEDAMAGE, WAITING;

    private static EventBingo activeEvent = NOTHING;

    public static EventBingo getActiveEvent() {
        return activeEvent;
    }

    public static void setActiveEvent(EventBingo activeEvent) {
        EventBingo.activeEvent = activeEvent;
    }

    private static void startDoubleDamageEvent() {
        setActiveEvent(DOUBLEDAMAGE);
        BossBar bar = Bukkit.createBossBar(ChatColor.DARK_RED + "Double Damage Event", BarColor.RED, BarStyle.SOLID, null);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1,1);
            all.sendTitle(ChatColor.DARK_RED + "Double damage Event started." , ChatColor.DARK_RED + "Damage taken gets doubled." , 1, 3, 1);
            bar.addPlayer(all);
        }
        final int[] timeTillNextEvent = {300};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.PLAYING)) {
                    if (timeTillNextEvent[0] > 0) {
                        timeTillNextEvent[0]--;
                        bar.setProgress((timeTillNextEvent[0] / 300) * 100);
                    }else {
                        bar.removeAll();
                        countdownToNextEvent();
                    }
                }
            }
        }.runTaskTimer(Bingoplugin.getPlugin(), 0, 20L);
    }

    private static void reshuffleBoard() {
        Collections.shuffle(BingoSettings.getItemsToObtain());
        for (BingoTeam team : Bingoplugin.getTeams()) {
            WinChecker.checkWin(team);
        }
        countdownToNextEvent();
    }

    private static void countdownToNextEvent() {
        setActiveEvent(WAITING);
        final int[] countdown = {new Random().nextInt(300) + 300};
        new BukkitRunnable() {

            @Override
            public void run() {
                if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.PLAYING)) {
                    if (countdown[0] > 0) {
                        countdown[0]--;
                    }else {
                        setActiveEvent(NOTHING);
                        startRandomEvent();
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Bingoplugin.getPlugin(), 0, 20L);
    }

    public static void startRandomEvent() {
        if (activeEvent.equals(NOTHING)) {
            if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.PLAYING)) {
                ArrayList<EventBingo> list = new ArrayList<>();
                list.addAll(Arrays.asList(EventBingo.values()));
                EventBingo random = list.get(new Random().nextInt(list.size() - 1));
                if (random.equals(DOUBLEDAMAGE)) {
                    startDoubleDamageEvent();
                }else if (random.equals(RESHUFFLE)) {
                    reshuffleBoard();
                }
            }
        }
    }
}
