package de.frxdy.bingo.listeners;

import de.frxdy.bingo.BingoEvents.EventBingo;
import de.frxdy.bingo.Utils.GamestateManager;
import de.frxdy.bingo.Utils.Settings.BingoSettings;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class damageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GamestateManager.gamestate == GamestateManager.Gamestate.PLAYING) {
            if (event.getEntity().getType().equals(EntityType.PLAYER)) {
                if (!BingoSettings.isTakeDamage()) {
                    event.setCancelled(true);
                }else {
                    if (EventBingo.getActiveEvent().equals(EventBingo.DOUBLEDAMAGE)) {
                        event.setDamage(event.getDamage() * 2);
                    }
                }
            }
        }else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (GamestateManager.gamestate == GamestateManager.Gamestate.PLAYING) {
            if (event.getEntity().getType().equals(EntityType.PLAYER)) {
                if (BingoSettings.isTakeDamage()) {
                    if (!BingoSettings.isPvp()) {
                        if (event.getDamager().equals(EntityType.PLAYER)) {
                            event.setCancelled(true);
                        }
                    }else {
                        if (EventBingo.getActiveEvent().equals(EventBingo.DOUBLEDAMAGE)) {
                            event.setDamage(event.getDamage() * 2);
                        }
                    }
                }else {
                    event.setCancelled(true);
                }
            }
        }else {
            event.setCancelled(true);
        }
    }

}
