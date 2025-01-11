package de.danzel34.challenges.challanges.challenges;

import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.challanges.Goal;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AllItemsChallenge extends Challenge {
    private final List<Material> itemsCollected = new ArrayList<>(); //todo saving works but loading not

    public AllItemsChallenge() {
        super("AllItems");
    }

    @Override
    public Goal challengeGoal() {
        return Goal.GET_ALL_ITEMS;
    }

    @Override
    public void onPickUp(EntityPickupItemEvent event) {
        checkWin(event.getItem().getItemStack().getType());
    }

    @Override
    public void onCraft(CraftItemEvent event) {
        checkWin(event.getRecipe().getResult().getType());
    }

    @Override
    public void onDrop(EntityDropItemEvent event) {
        if (event.getEntity() instanceof Player)
            checkWin(event.getItemDrop().getItemStack().getType());
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        checkWin(event.getCurrentItem().getType());
    }

    private void checkWin(@Nullable Material material) {
        if (material != null) {
            if (!itemsCollected.contains(material)) {
                itemsCollected.add(material);
                Bukkit.broadcast(Component.text("You collected " + material.name() + "!"));
            }
            if (itemsCollected.size() == Material.values().length) {
                win();
                Challenges.getInstance().getConfig().set("items-collected", List.of(Material.AIR.name()));
            }
        }
    }
}
