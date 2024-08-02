package de.frxdy.bingo;

import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Teams.TeamPicker;
import de.frxdy.bingo.Utils.JoinQuitListener;
import de.frxdy.bingo.Utils.Settings.SettingsListener;
import de.frxdy.bingo.Utils.timer.Timer;
import de.frxdy.bingo.commands.BingoCommand;
import de.frxdy.bingo.commands.GameCommand;
import de.frxdy.bingo.commands.Resetcommand;
import de.frxdy.bingo.commands.SkipItemCommand;
import de.frxdy.bingo.listeners.damageListener;
import de.frxdy.bingo.listeners.inventoryListener;
import de.frxdy.bingo.listeners.itemListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Bingoplugin extends JavaPlugin {

    private static Plugin plugin;

    private static List<BingoTeam> teams = new ArrayList<>();

    public static List<BingoTeam> getTeams() {
        return teams;
    }

    @Override
    public void onEnable() {
        plugin = this;

        commandregistration();
        listenerregistration();
        for (int i = 1; i < 6; i++) {
            getTeams().add(new BingoTeam(i));
        }
        Timer.updateTimer();
        Bukkit.getLogger().fine("Bingo plugin enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().fine("Bingo plugin disabled");
    }

    public void commandregistration() {
        getCommand("reset").setExecutor(new Resetcommand());
        getCommand("bingo").setExecutor(new BingoCommand());
        getCommand("game").setExecutor(new GameCommand());
        getCommand("game").setTabCompleter(new GameCommand());
        getCommand("skipItem").setExecutor(new SkipItemCommand());
    }

    public void listenerregistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new SettingsListener(), this);
        pluginManager.registerEvents(new itemListeners(), this);
        pluginManager.registerEvents(new damageListener(), this);
        pluginManager.registerEvents(new inventoryListener(), this);
        pluginManager.registerEvents(new TeamPicker(), this);
    }

    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Bingo" + ChatColor.DARK_GRAY + "] ";
    }

    public static BingoTeam getBingoTeamByPlayer(Player player) {
        for (BingoTeam team : getTeams()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public static BingoTeam getBingoTeamByTeamNumber(int number) {
        for (BingoTeam team : getTeams()) {
            if (team.getTeamNR() == number) {
                return team;
            }
        }
        return null;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
