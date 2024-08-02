package de.frxdy.bingo.forceItem;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForceItem {

    private static BossBar bar;

    public static void generateNewItem() {
        List<Material> items = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.isItem() && !material.name().contains("SPAWN_EGG") && !material.equals(Material.AIR)) {
                items.add(material);
            }
        }
        Material item = items.get(new Random().nextInt(items.size()));
        for (BingoTeam team : Bingoplugin.getTeams()) {
            team.getNeededItems().clear();
            team.getNeededItems().add(item);
        }
        if (bar != null) {
            bar.setTitle(ChatColor.GOLD + "Searched Item: " + ChatColor.GREEN + Bingoplugin.getTeams().get(0).getNeededItems().get(0));
        }
        displayItem();
    }

    private static void displayItem() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            for (Entity entity : all.getPassengers()) {
                all.removePassenger(entity);
                entity.remove();
            }
            ArmorStand stand = all.getLocation().getWorld().spawn(all.getLocation(), ArmorStand.class);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setSmall(true);
            stand.setArms(true);
            stand.setHelmet(new ItemStack(Bingoplugin.getTeams().get(0).getNeededItems().get(0)));
            all.setPassenger(stand);
        }
    }

    public static BossBar getBar() {
        return bar;
    }

    public static void setBar(BossBar bar) {
        ForceItem.bar = bar;
    }
}
