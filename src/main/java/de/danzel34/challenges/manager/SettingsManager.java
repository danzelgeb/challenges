package de.danzel34.challenges.manager;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsManager {
    private final Map<String, Boolean> settings = new HashMap<>();

    public SettingsManager(FileConfiguration config) {
        if (config.getConfigurationSection("settings") == null) return;
        if (config.getConfigurationSection("settings").getKeys(true).isEmpty()) return;

        for (String key : config.getConfigurationSection("settings").getKeys(true)) {
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
