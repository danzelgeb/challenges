package de.danzel34.challenges.listener;

import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!Challenges.getInstance().getSettingsManager().getSetting("move-without-started") &&
                !Challenges.getInstance().getSettingsManager().getSetting("challenge-over") &&
                !Challenges.getInstance().getTimerManager().isRunning()) {
            if (event.getFrom().getX() == event.getTo().getX() &&
                    event.getFrom().getY() == event.getTo().getY() &&
                    event.getFrom().getZ() == event.getTo().getZ()) return;
            event.setCancelled(true);
        }

        List<Challenge> activeChallenges = Challenges.getInstance().getChallengesManager().getActiveChallenges();
        activeChallenges.forEach(challenge -> challenge.onMove(event));
    }
}
