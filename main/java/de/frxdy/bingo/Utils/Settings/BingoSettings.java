package de.frxdy.bingo.Utils.Settings;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BingoSettings {

    private static int teamSize = 1;

    private static int time = 5400;

    private static int bingoSize = 5;

    private static int maxJokers = 0;

    private static boolean takeDamage = true;

    private static boolean pvp = false;

    private static List<Material> itemsToObtain = new ArrayList<>();

    private static boolean eventBingoActive = false;


    public static int getBingoSize() {
        return bingoSize;
    }

    public static List<Material> getItemsToObtain() {
        return itemsToObtain;
    }

    public static void setItemsToObtain(List<Material> itemsToObtain) {
        BingoSettings.itemsToObtain = itemsToObtain;
    }

    public static boolean isTakeDamage() {
        return takeDamage;
    }

    public static void setTakeDamage(boolean takeDamage) {
        BingoSettings.takeDamage = takeDamage;
    }

    public static boolean isPvp() {
        return pvp;
    }

    public static void setPvp(boolean pvp) {
        BingoSettings.pvp = pvp;
    }

    public static int getMaxJokers() {
        return maxJokers;
    }

    public static void setMaxJokers(int maxJokers) {
        BingoSettings.maxJokers = maxJokers;
    }

    public static void setBingoSize(int bingoSize) {
        BingoSettings.bingoSize = bingoSize;
    }

    public static int getTeamSize() {
        return teamSize;
    }

    public static void setTeamSize(int teamSize) {
        BingoSettings.teamSize = teamSize;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        BingoSettings.time = time;
    }

    public static boolean isEventBingoActive() {
        return eventBingoActive;
    }

    public static void setEventBingoActive(boolean eventBingoActive) {
        BingoSettings.eventBingoActive = eventBingoActive;
    }
}
