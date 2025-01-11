package de.danzel34.challenges.manager;

import de.danzel34.challenges.challanges.Challenge;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChallengesManager {
    private final Map<Challenge, Boolean> challenges = new HashMap<>();

    public ChallengesManager(List<Challenge> challengesList, FileConfiguration config) {
        if (config.getConfigurationSection("challenges") == null) {
            for (Challenge key : challengesList) {
                challenges.put(key, false);
            }
            return;
        }

        for (Challenge key : challengesList) {
            challenges.put(key, config.getBoolean("challenges." + key));
        }
    }

    public List<Challenge> getActiveChallenges() {
        return challenges.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Challenge> challenges() {
        return new ArrayList<>(challenges.keySet());
    }

    public Challenge getChallange(String name) {
        return challenges.keySet().stream()
                .filter(challenge -> challenge.getName().equals(name))
                .findFirst().orElse(null);
    }

    public <T extends Challenge> T getChallange(String name, Class<T> clazz) {
        return clazz.cast(challenges.keySet().stream()
                .filter(challenge -> challenge.getName().equals(name))
                .findFirst().orElse(null));
    }

    public boolean isActive(Challenge challenge) {
        return challenges.get(challenge);
    }

    public void setActive(Challenge challenge, boolean active) {
        challenges.put(challenge, active);
    }
}
