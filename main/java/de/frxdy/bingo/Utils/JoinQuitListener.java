package de.frxdy.bingo.Utils;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.forceItem.ForceItem;
import de.frxdy.bingo.scoreboard.ScoreboardHandler;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

public class JoinQuitListener implements Listener {

    public static ItemStack head;

    public static ItemStack settings;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + ">> " + ChatColor.DARK_GRAY + player.getName());
        if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.WAITING)) {
            if (player.isOp()) {
                settings = ItemBuilder.createItem(Material.NETHER_STAR, ChatColor.GOLD + "Settings", 1, null, null, true);
                player.getInventory().setItem(0, settings);
            }
            head = ItemBuilder.createSkull(ChatColor.GOLD + player.getName(), 1, null, null, true, player);
            player.getInventory().setItem(4, head);
        }else if (GamestateManager.gamestate.equals(GamestateManager.Gamestate.PLAYING)) {
            if (Bingoplugin.getBingoTeamByPlayer(player) == null) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Bingoplugin.getPrefix() + "Es läuft bereits ein Spiel...");
            }
            if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
                ArmorStand stand = player.getLocation().getWorld().spawn(player.getLocation(), ArmorStand.class);
                stand.setVisible(false);
                stand.setGravity(false);
                stand.setSmall(true);
                stand.setArms(true);
                stand.setHelmet(new ItemStack(Bingoplugin.getTeams().get(0).getNeededItems().get(0)));
                player.setPassenger(stand);
                ForceItem.getBar().addPlayer(player);
                Scoreboard scoreboard = ScoreboardHandler.updateScoreboard();
                player.setScoreboard(scoreboard);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for (Entity entity : event.getPlayer().getPassengers()) {
            event.getPlayer().removePassenger(entity);
            entity.remove();
        }
        event.setQuitMessage(ChatColor.RED + "<< " + ChatColor.DARK_GRAY + event.getPlayer().getName());
    }

}
