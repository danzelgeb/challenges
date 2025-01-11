package de.danzel34.challenges.listener;

import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!Challenges.getInstance().getTimerManager().isRunning()) {
            return;
        }

        if (event.getEntity() instanceof Player player) {
            if (player.getGameMode() == GameMode.SPECTATOR) return;
            //todo fail challenge if player dies
            Bukkit.broadcast(Component.text("The Challenge ended! The player " + player.getName() + " died!").color(NamedTextColor.RED));
        }

        List<Challenge> activeChallenges = Challenges.getInstance().getChallengesManager().getActiveChallenges();
        activeChallenges.forEach(challenge -> challenge.onDeath(event));
    }

}
