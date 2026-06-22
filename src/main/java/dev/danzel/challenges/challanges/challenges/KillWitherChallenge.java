package dev.danzel.challenges.challanges.challenges;

import dev.danzel.challenges.challanges.Challenge;
import dev.danzel.challenges.challanges.Goal;
import org.bukkit.Material;
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
    public Material getMaterial() {
        return Material.WITHER_SKELETON_SKULL;
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.WITHER) {
            win();
        }
    }
}
