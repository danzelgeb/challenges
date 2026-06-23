package dev.danzel.challenges.menu;

import dev.danzel.challenges.Challenges;
import dev.danzel.challenges.utils.Items;
import dev.danzel.challenges.utils.Txt;
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

import java.util.List;

public class SettingsGeneralMenu implements Listener {
    private static Inventory inventory;

    public static void register() {
        inventory = Bukkit.createInventory(null, 27,
                Txt.text("General Settings").color(NamedTextColor.BLUE).decoration(TextDecoration.BOLD, true));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, Items.placeholder());
        }

        Component loreOne = Challenges.getInstance().getSettingsManager().getSetting("move-without-started")
                ? Txt.text("Click to disable moving before challenge started").color(NamedTextColor.GRAY)
                : Txt.text("Click to enable moving before challenge started").color(NamedTextColor.GRAY);

        ItemStack toggleMoveBeforeStarted = ItemStack.of(Material.WATER_BUCKET);
        ItemMeta toggleMoveBeforeStartedMeta = toggleMoveBeforeStarted.getItemMeta();
        toggleMoveBeforeStartedMeta.displayName(Txt.text("Toggle Move Before Started"));
        toggleMoveBeforeStartedMeta.lore(List.of(
                loreOne
        ));
        if (Challenges.getInstance().getSettingsManager().getSetting("move-without-started")) toggleMoveBeforeStartedMeta.setEnchantmentGlintOverride(true);
        toggleMoveBeforeStarted.setItemMeta(toggleMoveBeforeStartedMeta);

        inventory.setItem(13, toggleMoveBeforeStarted);
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
                case WATER_BUCKET -> event.getWhoClicked().sendMessage(Component.text("Challenges"));
                case ARROW -> event.getWhoClicked().sendMessage(Component.text("Challenges")); //go back to other menu
                default -> event.getWhoClicked().sendMessage(Component.text("Unknown item clicked")); //close menu
            }
        }
    }
}
