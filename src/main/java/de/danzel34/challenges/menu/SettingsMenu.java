package de.danzel34.challenges.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsMenu implements Listener {
    private static Inventory inventory;

    public static void register() {
        inventory = Bukkit.createInventory(null, 27,
                Component.text("Settings").color(NamedTextColor.BLUE).decoration(TextDecoration.BOLD, true));

        ItemStack placeholder = ItemStack.of(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.displayName(Component.empty());
        placeholder.setItemMeta(placeholderMeta);

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, placeholder);
        }

        ItemStack challenges = ItemStack.of(Material.WATER_BUCKET);
        ItemMeta challengesMeta = challenges.getItemMeta();
        challengesMeta.displayName(Component.text("Challenges"));
        challenges.setItemMeta(challengesMeta);

        inventory.setItem(12, challenges);

        ItemStack general = ItemStack.of(Material.COMMAND_BLOCK_MINECART);
        ItemMeta generalMeta = general.getItemMeta();
        generalMeta.displayName(Component.text("General"));
        general.setItemMeta(generalMeta);

        inventory.setItem(14, general);
    }

    public static void open(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == inventory) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            switch (event.getCurrentItem().getType()) {
                case BLUE_STAINED_GLASS_PANE -> event.getWhoClicked().closeInventory();
                case WATER_BUCKET -> event.getWhoClicked().sendMessage(Component.text("Challenges"));
                case COMMAND_BLOCK_MINECART -> event.getWhoClicked().sendMessage(Component.text("General"));
            }
        }
    }
}
