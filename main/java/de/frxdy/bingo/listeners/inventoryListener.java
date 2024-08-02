package de.frxdy.bingo.listeners;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class inventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory().contains(ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false))) {
                event.setCancelled(true);
                if (event.isRightClick() && event.getCurrentItem().getEnchantments().isEmpty() && (!event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) && (!event.getCurrentItem().getType().equals(Material.BARRIER))) {
                    BingoTeam team = Bingoplugin.getBingoTeamByPlayer((Player) event.getWhoClicked());
                    assert team != null;
                    if (team.getJokersLeft() > 0) {
                        event.getClickedInventory().setItem(event.getSlot(), ItemBuilder.createItem(event.getCurrentItem().getType(), ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + event.getCurrentItem().getType().name().toLowerCase(Locale.ROOT), 1, Arrays.asList(Enchantment.MENDING), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
                        if (team.getJokersLeft() == 1) {
                            event.getClickedInventory().setItem(event.getClickedInventory().getSize() - 1, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
                        }else {
                            event.getClickedInventory().getItem(event.getClickedInventory().getSize() - 1).setAmount(event.getClickedInventory().getItem(event.getClickedInventory().getSize() - 1).getAmount() - 1);
                        }
                        Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Team " + team.getTeamNR() + " used a joker on: " + event.getCurrentItem().getType().name().toLowerCase());
                        team.getNeededItems().remove(event.getCurrentItem().getType());
                        team.setJokersLeft(team.getJokersLeft() - 1);
                    }else {
                        event.getWhoClicked().sendMessage(Bingoplugin.getPrefix() + "you don´t have any jokers left!");
                    }
                }
            }
        }
    }
}
