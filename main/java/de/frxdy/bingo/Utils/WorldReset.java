package de.frxdy.bingo.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;

public class WorldReset {

    public static void resetWorlds() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer(ChatColor.DARK_GRAY + "Welt wird zurückgesetzt...!\nBitte habe etwas geduld!");
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
            World world = Bukkit.getWorlds().get(i);
            Bukkit.unloadWorld(world, true);
            File worldFile = new File(world.getWorldFolder().getPath());
            deleteWorld(worldFile);
        }
    }

    private static boolean deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }

        return path.delete();
    }
}
