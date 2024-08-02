package de.frxdy.bingo.Utils;

import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    public static ItemStack createItem(Material material, String name, int amount, List<String> lore, List<ItemFlag> flags, boolean unbreakable) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        if (flags != null) {
            for (ItemFlag flag : flags) {
                meta.addItemFlags(flag);
            }
        }
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack createItem(Material material, String name, int amount, List<Enchantment> enchantments, List<String> lore, List<ItemFlag> flags, boolean unbreakable) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        if (enchantments != null) {
            for (Enchantment enchant : enchantments) {
                meta.addEnchant(enchant, 1, true);
            }
        }
        if (flags != null) {
            for (ItemFlag flag : flags) {
                meta.addItemFlags(flag);
            }
        }
        stack.setItemMeta(meta);
        return stack;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack createSkull(String name, int amount, List<String> lore, List<ItemFlag> flags, boolean unbreakable, Player skullowner) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        meta.setOwningPlayer(skullowner);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack createCustomSkull(String name, int amount, List<String> lore, List<ItemFlag> flags, boolean unbreakable, String textureValue) throws NoSuchFieldException, IllegalAccessException {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setUnbreakable(unbreakable);
        GameProfile profile = new GameProfile(UUID.randomUUID(), "Cock");

        profile.getProperties().put("textures", new Property("textures", textureValue));
        Field profileField = meta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        profileField.set(meta, profile);

        stack.setItemMeta(meta);
        return stack;
    }

}
