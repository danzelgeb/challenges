package dev.danzel.challenges.menu;

import dev.danzel.challenges.utils.Items;
import dev.danzel.challenges.utils.Txt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class ChallengeAllItemsCollectedMenu implements Listener {
    private static Inventory inventory;

    public static void register() {
        inventory = Bukkit.createInventory(null, 54, Txt.text("<blue><bold>Collected Items</bold></blue>"));

        for (int i = 0; i <= 53; i++) {
            if (i <= 8 || i >= 45) {
                inventory.setItem(i, Items.placeholder());
            }
        }
    }

    public static void update() {
        //todo reload all items
    }

    public static void open(Player player) {
        update();
        player.openInventory(inventory);
    }
}
