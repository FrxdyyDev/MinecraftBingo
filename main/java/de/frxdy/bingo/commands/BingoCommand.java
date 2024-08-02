package de.frxdy.bingo.commands;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.InventoryManager;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import de.frxdy.bingo.Utils.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class BingoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (GamestateManager.gamestate == GamestateManager.Gamestate.PLAYING) {
                    player.openInventory(Objects.requireNonNull(InventoryManager.createInv(BingoSettings.getBingoSize(), Bingoplugin.getBingoTeamByPlayer(player))));
                }
            }
        }
        return false;
    }
}
