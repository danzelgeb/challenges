package dev.danzel.challenges.listener;

import dev.danzel.challenges.Challenges;
import dev.danzel.challenges.challanges.Challenge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

import java.util.List;

public class EntityDropItemListener implements Listener {

    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event) {
        List<Challenge> activeChallenges = Challenges.getInstance().getChallengesManager().getActiveChallenges();
        activeChallenges.forEach(challenge -> challenge.onDrop(event));
    }

}
