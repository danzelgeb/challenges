package dev.danzel.challenges.listener;

import dev.danzel.challenges.Challenges;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && !Challenges.getInstance().getTimerManager().isRunning()) {
            event.setCancelled(true);
        }
    }
}
