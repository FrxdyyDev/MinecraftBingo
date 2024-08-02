package de.frxdy.bingo.commands;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.ItemBuilder;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import de.frxdy.bingo.Utils.timer.Timer;
import de.frxdy.bingo.forceItem.ForceItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GameCommand implements CommandExecutor, TabCompleter {

    private static HashMap<Player, Location> pausedLocationOfPlayer = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof  Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (player.isOp()) {
                    if (args[0].equals("pause")) {
                        GamestateManager.gamestate = GamestateManager.Gamestate.PAUSED;
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            pausedLocationOfPlayer.put(all, all.getLocation());
                        }
                        Timer.pause();
                    }else if (args[0].equals("resume")) {
                        GamestateManager.gamestate = GamestateManager.Gamestate.PLAYING;
                        for (Player all : pausedLocationOfPlayer.keySet()) {
                            all.teleport(pausedLocationOfPlayer.get(all));
                        }
                        Timer.resume();
                    }else if (args[0].equals("start")) {
                        Bukkit.broadcastMessage(Bingoplugin.getPrefix() + "Game is starting!");
                        createTeams();
                        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.CLASSIC_BINGO)) {
                            generateBingoBoard();
                            GamestateManager.startBingo();
                        }else {
                            GamestateManager.startForceItem();
                        }
                    }else if (args[0].equals("reset")) {
                        GamestateManager.gamestate = GamestateManager.Gamestate.WAITING;
                        Timer.reset();
                        if (ForceItem.getBar() != null) {
                            ForceItem.getBar().removeAll();
                        }
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                            for (Entity entity : all.getPassengers()) {
                                all.removePassenger(entity);
                                entity.remove();
                            }
                            if (all.isOp()) {
                                ItemStack settings = ItemBuilder.createItem(Material.NETHER_STAR, ChatColor.GOLD + "Settings", 1, null, null, true);
                                all.getInventory().setItem(0, settings);
                            }
                            ItemStack head = ItemBuilder.createSkull(ChatColor.GOLD + all.getName(), 1, null, null, true, player);
                            all.getInventory().setItem(4, head);
                        }
                        for (BingoTeam team : Bingoplugin.getTeams()) {
                            team.getPlayers().clear();
                            team.setCollectedItemsCount(0);
                            team.getNeededItems().clear();
                        }
                    }
                }
        }
    }
        return false;
    }

    private static void generateBingoBoard() {
        List<Material> items = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.isItem() && !material.name().contains("SPAWN_EGG") && !material.equals(Material.AIR)) {
                items.add(material);
            }
        }
        for (int i = 0; i < BingoSettings.getBingoSize()*BingoSettings.getBingoSize(); i++) {
            Material selectedItem = items.get(new Random().nextInt(items.size()));
            items.remove(selectedItem);
            BingoSettings.getItemsToObtain().add(selectedItem);
            for (BingoTeam team : Bingoplugin.getTeams()) {
                team.getNeededItems().add(selectedItem);
            }
        }
    }

    private static void createTeams() {
        boolean right = true;
        List<Object> players = Arrays.asList(Bukkit.getOnlinePlayers().stream().toArray());
        for (BingoTeam team : Bingoplugin.getTeams()) {
            for (int i = team.getPlayers().size(); i < BingoSettings.getTeamSize(); i++) {
                Player random = (Player) players.get(new Random().nextInt(players.size()));
                if (Bingoplugin.getBingoTeamByPlayer(random) == null) {
                    team.addPlayer(random);
                }
            }
            Location worldSpawn = Bukkit.getWorlds().get(0).getSpawnLocation();
            if (right) {
                team.setSpawnLocation(worldSpawn.add(team.getTeamNR() * 250, worldSpawn.getWorld().getHighestBlockYAt(team.getTeamNR() * 250, team.getTeamNR() * 250), team.getTeamNR() * 250));
                right = false;
            }else {
                team.setSpawnLocation(worldSpawn.add(team.getTeamNR() * -250, worldSpawn.getWorld().getHighestBlockYAt(team.getTeamNR() * -250, team.getTeamNR() * -250), team.getTeamNR() * -250));
                right = true;
            }
            team.setJokersLeft(BingoSettings.getMaxJokers());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("start");
            list.add("pause");
            list.add("resume");
            list.add("reset");
        }
        ArrayList<String> completerList = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for (String s : list) {
            s = s.toLowerCase();
            if (s.startsWith(current)) {
                completerList.add(s);
            }
        }
        return completerList;
    }

}
