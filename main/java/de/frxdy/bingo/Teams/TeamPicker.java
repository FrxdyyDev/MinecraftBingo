package de.frxdy.bingo.Teams;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Utils.ItemBuilder;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class TeamPicker implements Listener {

    private static Inventory teamsInv = null;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null) {
            if (event.getItem().equals(ItemBuilder.createSkull(ChatColor.GOLD + player.getName(), 1, null, null, true, player))) {
                if (teamsInv == null) {
                    teamsInv = generateTeamPickerInventory();
                }
                player.openInventory(teamsInv);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getItemInHand().equals(ItemBuilder.createSkull(ChatColor.GOLD + player.getName(), 1, null, null, true, player))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().equals(teamsInv)) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                if (event.getCurrentItem().getType() == Material.WHITE_BED) {
                    BingoTeam team = Bingoplugin.getBingoTeamByTeamNumber(Integer.parseInt(String.valueOf(event.getCurrentItem().getItemMeta().getDisplayName().toString().charAt(event.getCurrentItem().getItemMeta().getDisplayName().length() - 1))));
                    if (team != null) {
                        if (team.getPlayers().size() < BingoSettings.getTeamSize() && !team.getPlayers().contains(player)) {
                            BingoTeam currentTeam = Bingoplugin.getBingoTeamByPlayer(player);
                            if (currentTeam != null) {
                                currentTeam.getPlayers().remove(player);
                                ArrayList<String> lore = new ArrayList<>();
                                lore.add(ChatColor.DARK_BLUE + "Team - Members:");
                                for (Player teamPlayer : currentTeam.getPlayers()) {
                                    lore.add(ChatColor.DARK_GRAY + "- " + teamPlayer.getName());
                                }
                                event.getClickedInventory().setItem((currentTeam.getTeamNR() - 1) * 2, ItemBuilder.createItem(Material.WHITE_BED, ChatColor.GOLD + "Team " + currentTeam.getTeamNR(), 1, lore, null, false));
                            }
                            team.addPlayer(player);
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.DARK_BLUE + "Team - Members:");
                            for (Player teamPlayer : team.getPlayers()) {
                                lore.add(ChatColor.DARK_GRAY + "- " + teamPlayer.getName());
                            }
                            event.getClickedInventory().setItem(event.getSlot(), ItemBuilder.createItem(Material.WHITE_BED, ChatColor.GOLD + "Team " + team.getTeamNR(), 1, lore, null, false));
                        }
                    }
                }
            }
        }
    }

    private static Inventory generateTeamPickerInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Choose your Team!");
        int offset = 0;
        for (BingoTeam team : Bingoplugin.getTeams()) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_BLUE + "Team - Members:");
            for (Player player : team.getPlayers()) {
                lore.add(ChatColor.DARK_GRAY + "- " + player.getName());
            }
            inventory.setItem(offset, ItemBuilder.createItem(Material.WHITE_BED, ChatColor.GOLD + "Team " + team.getTeamNR(), 1, lore, null, false));
            offset += 2;
        }
        return inventory;
    }

}
