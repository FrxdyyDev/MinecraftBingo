package de.frxdy.bingo.commands;

import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.forceItem.ForceItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkipItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
            if (sender.isOp()) {
                ForceItem.generateNewItem();
            }
        }
        return false;
    }
}
