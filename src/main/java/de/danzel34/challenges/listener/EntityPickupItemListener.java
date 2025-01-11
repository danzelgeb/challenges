package de.danzel34.challenges.listener;

import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.List;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        List<Challenge> activeChallenges = Challenges.getInstance().getChallengesManager().getActiveChallenges();
        activeChallenges.forEach(challenge -> challenge.onPickUp(event));
    }

}
