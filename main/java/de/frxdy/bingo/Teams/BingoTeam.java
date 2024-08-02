package de.frxdy.bingo.Teams;

import de.frxdy.bingo.Utils.ItemBuilder;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BingoTeam {

    private int teamNR;

    private int maxTeamMembers = 1;

    private List<Player> players = new ArrayList<>();

    private List<Material> neededItems = new ArrayList<>();

    private int collectedItemsCount = 0;

    private int jokersLeft = 0;

    private Location spawnLocation = null;

    public BingoTeam(int teamNr, int maxTeamMembers) {
        this.setTeamNR(teamNr);
        if (maxTeamMembers > 1) {
            this.setMaxTeamMembers(maxTeamMembers);
        }
    }

    public BingoTeam(int teamNR) {
        this.teamNR = teamNR;
    }

    private void endGame() {
        Bukkit.broadcastMessage("WIN: " + getTeamNR());
    }

    public void addPlayer(Player player) {
        if (players.size() < maxTeamMembers) {
            players.add(player);
        }
    }

    public int getTeamNR() {
        return teamNR;
    }

    public void setTeamNR(int teamNR) {
        this.teamNR = teamNR;
    }

    public int getMaxTeamMembers() {
        return maxTeamMembers;
    }

    public void setMaxTeamMembers(int maxTeamMembers) {
        this.maxTeamMembers = maxTeamMembers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Material> getNeededItems() {
        return neededItems;
    }

    public void setNeededItems(List<Material> neededItems) {
        this.neededItems = neededItems;
    }

    public int getJokersLeft() {
        return jokersLeft;
    }

    public void setJokersLeft(int jokersLeft) {
        this.jokersLeft = jokersLeft;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public int getCollectedItemsCount() {
        return collectedItemsCount;
    }

    public void setCollectedItemsCount(int collectedItemsCount) {
        this.collectedItemsCount = collectedItemsCount;
    }
}
