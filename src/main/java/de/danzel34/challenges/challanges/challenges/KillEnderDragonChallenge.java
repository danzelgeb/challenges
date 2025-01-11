package de.danzel34.challenges.challanges.challenges;

import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.challanges.Goal;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEnderDragonChallenge extends Challenge {
    public KillEnderDragonChallenge() {
        super("KillEnderDragon");
    }

    @Override
    public Goal challengeGoal() {
        return Goal.KILL_ENDER_DRAGON;
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.ENDER_DRAGON) {
            win();
        }
    }
}
