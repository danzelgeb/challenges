package de.danzel34.challenges.challanges.challenges;

import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.challanges.Goal;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;

public class NoCraftingTableChallenge extends Challenge {
    public NoCraftingTableChallenge() {
        super("NoCraftingTable");
    }

    @Override
    public Goal challengeGoal() {
        return Goal.KILL_ENDER_DRAGON;
    }

    @Override
    public Material getMaterial() {
        return Material.CRAFTING_TABLE;
    }

    @Override
    public void onCraft(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType() == Material.CRAFTING_TABLE) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == org.bukkit.entity.EntityType.ENDER_DRAGON) {
            win();
        }
    }
}
