package de.frxdy.bingo.commands;

import de.frxdy.bingo.Utils.GamestateManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Resetcommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.isOp()) {
            GamestateManager.resetBingo();
        }
        return false;
    }
}
