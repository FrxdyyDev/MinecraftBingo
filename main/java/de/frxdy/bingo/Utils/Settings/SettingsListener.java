package de.frxdy.bingo.Utils.Settings;

import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SettingsListener implements Listener {

    Inventory CB_SettingsInventory;

    Inventory CB_TableSettingsInv;

    Inventory FI_SettingsInv;

    Inventory gameModeInv;

    @EventHandler
    public void onClick(InventoryClickEvent event) throws NoSuchFieldException, IllegalAccessException {
        if (event.getClickedInventory() != null) {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory().equals(CB_SettingsInventory)) {
                event.setCancelled(true);
                if (event.getSlot() == 1) {
                    if (CB_SettingsInventory.getItem(10).getAmount() < 20) {
                        CB_SettingsInventory.getItem(10).setAmount(CB_SettingsInventory.getItem(10).getAmount() + 1);
                        BingoSettings.setTeamSize(CB_SettingsInventory.getItem(10).getAmount());
                    }
                }else if (event.getSlot() == 19) {
                    if (CB_SettingsInventory.getItem(10).getAmount() > 1) {
                        CB_SettingsInventory.getItem(10).setAmount(CB_SettingsInventory.getItem(10).getAmount() - 1);
                        BingoSettings.setTeamSize(CB_SettingsInventory.getItem(10).getAmount());
                    }
                }else if (event.getSlot() == 7) {
                    BingoSettings.setTime(BingoSettings.getTime() + 30);
                    CB_SettingsInventory.setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "max. Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime()/60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() %60)).toString()), null, false));
                }else if (event.getSlot() == 25) {
                    if (BingoSettings.getTime() >= 30) {
                        BingoSettings.setTime(BingoSettings.getTime() - 30);
                        CB_SettingsInventory.setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "max. Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime()/60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() %60)).toString()), null, false));
                    }
                }else if (event.getSlot() == 4) {
                    if (CB_TableSettingsInv == null) {
                        CB_TableSettingsInv = createTableSettingsInv();
                    }
                    player.openInventory(CB_TableSettingsInv);
                }else if (event.getSlot() == 22) {
                    BingoSettings.setEventBingoActive(!BingoSettings.isEventBingoActive());
                    if (BingoSettings.isEventBingoActive()) {
                        event.getClickedInventory().setItem(22, ItemBuilder.createItem(Material.FIREWORK_ROCKET, ChatColor.GOLD + "Event-Bingo: " + ChatColor.GREEN + "active", 1, Arrays.asList(Enchantment.MENDING) , null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
                    }else {
                        event.getClickedInventory().setItem(22, ItemBuilder.createItem(Material.FIREWORK_ROCKET, ChatColor.GOLD + "Event-Bingo: " + ChatColor.RED + "not active", 1, null, null, false));
                    }
                }
            }else if (event.getClickedInventory().equals(CB_TableSettingsInv)) {
                event.setCancelled(true);
                if (event.getSlot() == 1) {
                    if (BingoSettings.getMaxJokers() == 0) {
                        BingoSettings.setMaxJokers(BingoSettings.getMaxJokers() + 1);
                        CB_TableSettingsInv.setItem(10, ItemBuilder.createItem(Material.BARRIER, ChatColor.GOLD + "max. Jokers per team: " + BingoSettings.getMaxJokers(), BingoSettings.getMaxJokers(), null, null, false));
                    }else {
                        if (CB_TableSettingsInv.getItem(10).getAmount() < (BingoSettings.getBingoSize())/3) {
                            CB_TableSettingsInv.getItem(10).setAmount(CB_TableSettingsInv.getItem(10).getAmount() + 1);
                            BingoSettings.setMaxJokers(BingoSettings.getMaxJokers() + 1);
                        }
                    }
                }else if (event.getSlot() == 19) {
                    if (BingoSettings.getMaxJokers() > 1) {
                        CB_TableSettingsInv.getItem(10).setAmount(CB_TableSettingsInv.getItem(10).getAmount() - 1);
                        BingoSettings.setMaxJokers(BingoSettings.getMaxJokers() - 1);
                    } else if (BingoSettings.getMaxJokers() == 1) {
                        CB_TableSettingsInv.setItem(10, ItemBuilder.createItem(Material.STRUCTURE_VOID, ChatColor.GOLD + "max. Jokers per team: " + BingoSettings.getMaxJokers(), 1, null, null, false));
                        BingoSettings.setMaxJokers(BingoSettings.getMaxJokers() - 1);
                    }
                }else if (event.getSlot() == 4) {
                    if (BingoSettings.getBingoSize() < 5) {
                        BingoSettings.setBingoSize(BingoSettings.getBingoSize() + 2);
                        CB_TableSettingsInv.setItem(13, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Bingotable-Size: " + BingoSettings.getBingoSize(), 1, null, null, false));
                    }
                }else if (event.getSlot() == 22) {
                    if (BingoSettings.getBingoSize() > 3) {
                        BingoSettings.setBingoSize(BingoSettings.getBingoSize() - 2);
                        CB_TableSettingsInv.setItem(13, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Bingotable-Size: " + BingoSettings.getBingoSize(), 1, null, null, false));
                    }
                }else if (event.getSlot() == 13) {
                    if (CB_SettingsInventory == null) {
                        CB_SettingsInventory = createInv();
                    }
                    player.openInventory(CB_SettingsInventory);
                }
            }else if (event.getClickedInventory().equals(gameModeInv)) {
                event.setCancelled(true);
                if (event.getSlot() == 11) {
                    if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.CLASSIC_BINGO)) {
                        player.openInventory(CB_SettingsInventory);
                    }else {
                        GamestateManager.gamemode = GamestateManager.BingoMode.CLASSIC_BINGO;
                        if (CB_SettingsInventory == null) {
                            CB_SettingsInventory = createInv();
                        }
                        player.openInventory(gameModeInv = createGamemodeInv());
                    }
                }else if (event.getSlot() == 15) {
                    if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
                        if (FI_SettingsInv == null) {
                            FI_SettingsInv = createFI_SettingsInv();
                        }
                        player.openInventory(FI_SettingsInv);
                    }else {
                        GamestateManager.gamemode = GamestateManager.BingoMode.FORCE_ITEM;
                        player.openInventory(gameModeInv = createGamemodeInv());
                    }
                }
            }else if (event.getClickedInventory().equals(FI_SettingsInv)) {
                if (event.getSlot() == 1) {
                    if (event.getClickedInventory().getItem(10).getAmount() < 20) {
                        event.getClickedInventory().getItem(10).setAmount(event.getClickedInventory().getItem(10).getAmount() + 1);
                        BingoSettings.setTeamSize(event.getClickedInventory().getItem(10).getAmount());
                    }
                }else if (event.getSlot() == 19) {
                    if (event.getClickedInventory().getItem(10).getAmount() > 1) {
                        event.getClickedInventory().getItem(10).setAmount(event.getClickedInventory().getItem(10).getAmount() - 1);
                        BingoSettings.setTeamSize(event.getClickedInventory().getItem(10).getAmount());
                    }
                }else if (event.getSlot() == 7) {
                    BingoSettings.setTime(BingoSettings.getTime() + 30);
                    event.getClickedInventory().setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "max. Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime()/60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() %60)).toString()), null, false));
                }else if (event.getSlot() == 25) {
                    if (BingoSettings.getTime() >= 30) {
                        BingoSettings.setTime(BingoSettings.getTime() - 30);
                        event.getClickedInventory().setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "max. Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime() / 60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() % 60)).toString()), null, false));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws NoSuchFieldException, IllegalAccessException {
        if (gameModeInv == null) {
            gameModeInv = createGamemodeInv();
        }
        Player player = event.getPlayer();
        try {
            if (event.getItem().getType().equals(Material.NETHER_STAR)) {
                if (GamestateManager.gamestate == GamestateManager.Gamestate.WAITING) {
                    event.setCancelled(true);
                    player.openInventory(gameModeInv);
                }
            }
        }catch (Exception e) {
        }
    }

    private static Inventory createGamemodeInv() {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.GOLD + "Settings");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
        }
        if (GamestateManager.gamemode.equals(GamestateManager.BingoMode.FORCE_ITEM)) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_BLUE + "Force-Item Battle is activated!");
            lore.add(ChatColor.DARK_BLUE + "[+] Click to get to settings!");
            lore.add(ChatColor.DARK_BLUE + "[+] Activate Classic Bingo to " + ChatColor.RED + "deactivate " + ChatColor.DARK_BLUE + "it!");
            inventory.setItem(15, ItemBuilder.createItem(Material.BEACON, ChatColor.GOLD + "Force-Item: " + ChatColor.GREEN + "active", 1, Arrays.asList(Enchantment.MENDING), lore, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
            lore.clear();
            lore.add(ChatColor.DARK_BLUE + "Classic Bingo is deactivated");
            lore.add(ChatColor.DARK_BLUE + "[+] Click to activate it!");
            inventory.setItem(11, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Classic Bingo: " + ChatColor.RED + "not active", 1, lore, null, false));
        }else {
            GamestateManager.gamemode = GamestateManager.BingoMode.CLASSIC_BINGO;
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_BLUE + "Classic Bingo is activated!");
            lore.add(ChatColor.DARK_BLUE + "[+] Click to get to settings!");
            lore.add(ChatColor.DARK_BLUE + "[+] Activate Force-Item Battle to " + ChatColor.RED + "deactivate it!");
            inventory.setItem(11, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Classic Bingo: " + ChatColor.GREEN + "active", 1, Arrays.asList(Enchantment.MENDING), lore, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
            lore.clear();
            lore.add(ChatColor.DARK_BLUE + "Force-Item Battle is deactivated");
            lore.add(ChatColor.DARK_BLUE + "[+] Click to activate it!");
            inventory.setItem(15, ItemBuilder.createItem(Material.BEACON, ChatColor.GOLD + "Force-Item: " + ChatColor.RED + "not active", 1, lore, null, false));
        }
        return inventory;
    }

    private static Inventory createFI_SettingsInv() throws NoSuchFieldException, IllegalAccessException {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.GOLD + "Settings");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
        }
        inventory.setItem(10, ItemBuilder.createItem(Material.WHITE_BED, ChatColor.GOLD + "max. Team Size", BingoSettings.getTeamSize(), null, null, false));
        inventory.setItem(1, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(19, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));

        inventory.setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime()/60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() %60)).toString()), null, false));
        inventory.setItem(7, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+30 sec.", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(25, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-30 sec.", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));

        return inventory;
    }

    public static Inventory createTableSettingsInv() throws NoSuchFieldException, IllegalAccessException {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.GOLD + "Settings");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
        }
        if (BingoSettings.getMaxJokers() > 0) {
            inventory.setItem(10, ItemBuilder.createItem(Material.BARRIER, ChatColor.GOLD + "max. Jokers per team: " + BingoSettings.getMaxJokers(), BingoSettings.getMaxJokers(), null, null, false));
        }else {
            inventory.setItem(10, ItemBuilder.createItem(Material.STRUCTURE_VOID, ChatColor.GOLD + "max. Jokers per team: " + BingoSettings.getMaxJokers(), 1, null, null, false));
        }
        inventory.setItem(1, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(19, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));

        inventory.setItem(13, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Bingotable-Size: " + BingoSettings.getBingoSize(), 1, null, null, false));
        inventory.setItem(4, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(22, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));
        return inventory;
    }

    public static Inventory createInv() throws NoSuchFieldException, IllegalAccessException {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.GOLD + "Settings");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", 1, null, null, false));
        }
        inventory.setItem(10, ItemBuilder.createItem(Material.WHITE_BED, ChatColor.GOLD + "max. Team Size", BingoSettings.getTeamSize(), null, null, false));
        inventory.setItem(1, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(19, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-1", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));

        inventory.setItem(4, ItemBuilder.createItem(Material.CRAFTING_TABLE, ChatColor.GOLD + "Bingotable-Size", 1, null, null, false));
        if (BingoSettings.isEventBingoActive()) {
            inventory.setItem(22, ItemBuilder.createItem(Material.FIREWORK_ROCKET, ChatColor.GOLD + "Event-Bingo: " + ChatColor.GREEN + "active", 1, Arrays.asList(Enchantment.MENDING) , null, Collections.singletonList(ItemFlag.HIDE_ENCHANTS), false));
        }else {
            inventory.setItem(22, ItemBuilder.createItem(Material.FIREWORK_ROCKET, ChatColor.GOLD + "Event-Bingo: " + ChatColor.RED + "not active", 1, null, null, false));
        }


        inventory.setItem(16, ItemBuilder.createItem(Material.CLOCK, ChatColor.GOLD + "Playtime", 1, Arrays.asList(new StringBuilder().append(String.format("%02d", BingoSettings.getTime()/3600 % 24)).append(":").append(String.format("%02d", BingoSettings.getTime()/60 % 60)).append(":").append(String.format("%02d", BingoSettings.getTime() %60)).toString()), null, false));
        inventory.setItem(7, ItemBuilder.createCustomSkull(ChatColor.GOLD + "+30 sec.", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzOGQwOGJkMDQwNWMwNWY0N2VhODZkNjY2NDM0MzRmZGQyZThjNDZmZjFlNmY4ODJiYjliZjg5MWM3ZDNhNSJ9fX0="));
        inventory.setItem(25, ItemBuilder.createCustomSkull(ChatColor.GOLD + "-30 sec.", 1, null, null, false, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDczZmQ4YTA2ZTZlYTgyMDc5NGNkYTIxNGZjNDZiNGMzMjlmYTlhZjMyNGU0NGVhYjQ0OTZjMmQ5ZjViYTZmZCJ9fX0="));

        return inventory;
    }



}
