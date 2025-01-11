package de.danzel34.challenges.listener;

import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.List;

public class CraftItemListener implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        List<Challenge> activeChallenges = Challenges.getInstance().getChallengesManager().getActiveChallenges();
        activeChallenges.forEach(challenge -> challenge.onCraft(event));
    }
}
