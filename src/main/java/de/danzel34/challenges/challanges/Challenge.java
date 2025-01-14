package de.danzel34.challenges.challanges;

import de.danzel34.challenges.Challenges;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@Getter
public abstract class Challenge {
    private final String name;

    public Challenge(String name) {
        this.name = name;
    }

    public abstract Goal challengeGoal();

    public abstract Material getMaterial();

    public void onMove(PlayerMoveEvent event) {}

    public void onDamage(EntityDamageByEntityEvent event) {}

    public void onDeath(EntityDeathEvent event) {}

    public void onDrop(EntityDropItemEvent event) {}

    public void onPickUp(EntityPickupItemEvent event) {}

    public void onCraft(CraftItemEvent event) {}

    public void onInventoryClick(InventoryClickEvent event) {}

    public void win() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(Component.text("The Challenge ended!").color(NamedTextColor.GREEN));
        }
        Challenges.getInstance().getSettingsManager().setSetting("challenge-over", true);
        Challenges.getInstance().getTimerManager().stop();
    }
}
