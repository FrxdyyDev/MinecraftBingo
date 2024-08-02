package de.frxdy.bingo.Utils;

import de.frxdy.bingo.Teams.BingoTeam;
import de.frxdy.bingo.Utils.ItemBuilder;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class InventoryManager {

    public static Inventory createInv(int bingoSize, BingoTeam team) {
        if (bingoSize <= 7) {
            if (bingoSize % 2 == 1) {
                Inventory inventory = Bukkit.createInventory(null, 9*bingoSize, ChatColor.GOLD + "BINGO");
                int offset = (9-bingoSize)/2;
                for (int row = 0; row < (bingoSize + 1); row++) {
                    for (int x = 0; x < offset; x++) {
                        if (row < bingoSize) {
                            inventory.setItem(row*9+x, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
                        }
                    }
                    for (int x = 1; x < offset + 1; x++) {
                        if (row > 0) {
                            inventory.setItem(row*9-x, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
                        }
                    }
                }
                int current = 0;
                for (int i = 0; i < inventory.getSize(); i++) {
                    if (inventory.getItem(i) == null) {
                        Material materialToObtain = BingoSettings.getItemsToObtain().get(current);
                        if (team != null) {
                            if (team.getNeededItems().contains(materialToObtain)) {
                                inventory.setItem(i, ItemBuilder.createItem(materialToObtain, ChatColor.GOLD + materialToObtain.name().toLowerCase(Locale.ROOT), 1, null, null, false));
                            }else {
                                inventory.setItem(i, ItemBuilder.createItem(materialToObtain, ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + materialToObtain.name().toLowerCase(Locale.ROOT), 1, Arrays.asList(Enchantment.MENDING), null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
                            }
                        }
                        current++;
                    }
                }
                if (bingoSize == 5 && team.getJokersLeft() > 0) {
                    inventory.setItem(inventory.getSize() - 1, ItemBuilder.createItem(Material.BARRIER, ChatColor.GOLD + "Joker", team.getJokersLeft(), null, null, false));
                }else if (bingoSize == 7 && team.getJokersLeft() > 0) {
                    inventory.setItem(inventory.getSize() - 1, ItemBuilder.createItem(Material.BARRIER, ChatColor.GOLD + "Joker", team.getJokersLeft(), null, null, false));
                }
                return inventory;
            }else {
                return createInv(bingoSize + 1, team);
            }
        }else {
            return null;
        }
    }

}
