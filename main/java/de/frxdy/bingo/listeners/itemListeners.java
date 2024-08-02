package de.frxdy.bingo.listeners;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.WinChecker;
import de.frxdy.bingo.forceItem.ForceItem;
import de.frxdy.bingo.scoreboard.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Formattable;
import java.util.Locale;

public class itemListeners implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        BingoTeam team = Bingoplugin.getBingoTeamByPlayer(player);
        Material item = event.getRecipe().getResult().getType();
        assert team != null;
        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.CLASSIC_BINGO)) {
            if (team.getNeededItems().contains(item)) {
                team.getNeededItems().remove(item);
                Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Team " + team.getTeamNR() + " found " + item.name().toLowerCase(Locale.ENGLISH) + "!");
                WinChecker.checkWin(team);
            }
        }else if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
            if (team.getNeededItems().contains(item)) {
                Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Team " + team.getTeamNR() + " found " + item.name().toLowerCase(Locale.ENGLISH) + "!");
                team.setCollectedItemsCount(team.getCollectedItemsCount() + 1);
                ForceItem.generateNewItem();
                Scoreboard scoreboard = ScoreboardHandler.updateScoreboard();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setScoreboard(scoreboard);
                }
            }
        }
    }

    @EventHandler
    public void onCollect(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Material item = event.getItem().getItemStack().getType();
        BingoTeam team = Bingoplugin.getBingoTeamByPlayer(player);
        assert team != null;
        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.CLASSIC_BINGO)) {
            if (team.getNeededItems().contains(item)) {
                team.getNeededItems().remove(item);
                Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Team " + team.getTeamNR() + " found " + item.name().toLowerCase(Locale.ENGLISH) + "!");
                WinChecker.checkWin(team);
            }
        }else if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
            if (team.getNeededItems().contains(item)) {
                Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Team " + team.getTeamNR() + " found " + item.name().toLowerCase(Locale.ENGLISH) + "!");
                team.setCollectedItemsCount(team.getCollectedItemsCount() + 1);
                ForceItem.generateNewItem();
                Scoreboard scoreboard = ScoreboardHandler.updateScoreboard();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setScoreboard(scoreboard);
                }
            }
        }
    }

}
