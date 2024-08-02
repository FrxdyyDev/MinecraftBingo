package de.frxdy.bingo.Utils;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class WinChecker {

    public static void checkWin(BingoTeam team) {
        if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.PLAYING)) {
            if (checkRows(team) || checkLines(team) || checkDiagonales(team)) {
                endGame(team);
            }
        }
    }

    private static boolean checkRows(BingoTeam team) {
        Inventory inventory = InventoryManager.createInv(BingoSettings.getBingoSize(), team);
        for (int i = 0; i < inventory.getSize()/9; i++) {
            int streak = 0;
            for (int i2 = 2; i2 < 7; i2++) {
                if (!inventory.getItem((i*9)+i2).getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                    if (!inventory.getItem((i*9)+i2).getEnchantments().isEmpty()) {
                        streak++;
                    }else {
                        streak = 0;
                    }
                }
            }
            if (streak == BingoSettings.getBingoSize()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkLines(BingoTeam team) {
        Inventory inventory = InventoryManager.createInv(BingoSettings.getBingoSize(), team);
        for (int i = 2; i < 7; i++) {
            int streak = 0;
            for (int i2 = 0; i2 < inventory.getSize(); i2++) {
                if (!inventory.getItem((i2*9)+i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                    if (!inventory.getItem((i2*9)+i).getEnchantments().isEmpty()) {
                        streak++;
                    }else {
                        streak = 0;
                    }
                }
            }
            if (streak == BingoSettings.getBingoSize()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonales(BingoTeam team) {
        Inventory inventory = InventoryManager.createInv(BingoSettings.getBingoSize(), team);
        int i = 0;
        while (inventory.getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            i++;
        }
        while (!inventory.getItem(i).getEnchantments().isEmpty()) {
            i+=10;
        }
        if (i > inventory.getSize() - 9 && i < inventory.getSize()) {
            return true;
        }
        i = 0;
        while (inventory.getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            i++;
        }
        while (!inventory.getItem(i).getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
            i++;
        }
        i--;
        while (!inventory.getItem(i).getEnchantments().isEmpty()) {
            i+=8;
        }
        if (i > inventory.getSize() - 9 && i < inventory.getSize()) {
            return true;
        }else {
            return false;
        }
    }

    public static void endGame(BingoTeam team) {
        Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Winner Team: " + team.getTeamNR());
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setGameMode(GameMode.SPECTATOR);
        }
    }

}
