package de.frxdy.bingo.scoreboard;

import de.frxdy.bingo.Bingoplugin;
import de.frxdy.bingo.Teams.BingoTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardHandler {


    public static Scoreboard updateScoreboard() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("sb", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "ForceItem");

        //sort Teams
        List<BingoTeam> sortedTeams = new ArrayList<>();
        for (int i = 0; i < Bingoplugin.getTeams().size(); i++) {
            BingoTeam lowest = Bingoplugin.getTeams().get(0);
            for (int i2 = 0; i2 < Bingoplugin.getTeams().size(); i2++) {
                if (Bingoplugin.getTeams().get(i2).getCollectedItemsCount() > 0) {
                    if (!sortedTeams.contains(Bingoplugin.getTeams().get(i2))) {
                        if (lowest.getCollectedItemsCount() > Bingoplugin.getTeams().get(i2).getCollectedItemsCount()) {
                            lowest = Bingoplugin.getTeams().get(i2);
                        }
                    }
                }else {
                    if (!sortedTeams.contains(Bingoplugin.getTeams().get(i2))) {
                        sortedTeams.add(Bingoplugin.getTeams().get(i2));
                    }
                }
            }
            if (!sortedTeams.contains(lowest)) {
                sortedTeams.add(lowest);
            }
        }
        int x = 0;
        for (BingoTeam team : sortedTeams) {
            objective.getScore(ChatColor.DARK_GRAY + "" + team.getTeamNR() + ": " + team.getCollectedItemsCount()).setScore(x);
            x++;
        }
        return scoreboard;

    }

}
