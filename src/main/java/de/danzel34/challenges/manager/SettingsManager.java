package de.danzel34.challenges.manager;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class SettingsManager {
    private final Map<String, Boolean> settings = new HashMap<>();

    public SettingsManager(FileConfiguration config) {
        Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("settings")).getKeys(true);

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            settings.put(key, config.getBoolean("settings." + key));
        }
    }

    public boolean getSetting(String setting) {
        return settings.getOrDefault(setting, false);
    }

    public void setSetting(String setting, Boolean value) {
        settings.put(setting, value);
    }

    public List<String> getSettings() {
        return settings.keySet().stream().toList();
    }
}
