package dev.danzel.challenges.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    public static ItemStack placeholder() {
        ItemStack placeholder = ItemStack.of(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.displayName(Component.empty());
        placeholder.setItemMeta(placeholderMeta);
        return placeholder;
    }

}
