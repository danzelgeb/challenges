package de.danzel34.challenges.challanges.challenges;

import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.challanges.Goal;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillWitherChallenge extends Challenge {

    public KillWitherChallenge() {
        super("KillWither");
    }

    @Override
    public Goal challengeGoal() {
        return Goal.KILL_WITHER;
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.WITHER) {
            win();
        }
    }
}
