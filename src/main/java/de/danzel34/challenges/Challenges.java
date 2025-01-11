package de.danzel34.challenges;

import co.aikar.commands.PaperCommandManager;
import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.challanges.challenges.AllItemsChallenge;
import de.danzel34.challenges.challanges.challenges.KillEnderDragonChallenge;
import de.danzel34.challenges.challanges.challenges.KillWitherChallenge;
import de.danzel34.challenges.challanges.challenges.NoCraftingTableChallenge;
import de.danzel34.challenges.command.ChallengeSpecificCommands;
import de.danzel34.challenges.command.SettingsCommand;
import de.danzel34.challenges.command.StartCommand;
import de.danzel34.challenges.command.TimerCommand;
import de.danzel34.challenges.listener.CraftItemListener;
import de.danzel34.challenges.listener.EntityDamageByEntityListener;
import de.danzel34.challenges.listener.EntityDamageListener;
import de.danzel34.challenges.listener.EntityDeathListener;
import de.danzel34.challenges.listener.EntityDropItemListener;
import de.danzel34.challenges.listener.EntityPickupItemListener;
import de.danzel34.challenges.listener.InventoryClickListener;
import de.danzel34.challenges.listener.PlayerJoinListener;
import de.danzel34.challenges.listener.PlayerMoveListener;
import de.danzel34.challenges.listener.PlayerQuitListener;
import de.danzel34.challenges.manager.ChallengesManager;
import de.danzel34.challenges.manager.SettingsManager;
import de.danzel34.challenges.manager.TimerManager;
import de.danzel34.challenges.menu.SettingsMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class Challenges extends JavaPlugin {
    @Getter
    private static Challenges instance;
    private PaperCommandManager commandManager;
    private ChallengesManager challengesManager;
    private SettingsManager settingsManager;
    private TimerManager timerManager;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        commandManager = new PaperCommandManager(this);
        registerListener(new CraftItemListener());
        registerListener(new EntityDamageByEntityListener());
        registerListener(new EntityDamageListener());
        registerListener(new EntityDeathListener());
        registerListener(new EntityDropItemListener());
        registerListener(new EntityPickupItemListener());
        registerListener(new InventoryClickListener());
        registerListener(new PlayerJoinListener());
        registerListener(new PlayerMoveListener());
        registerListener(new PlayerQuitListener());
        registerListener(new SettingsMenu());
        loadTimer();
        challengesManager = new ChallengesManager(registerChallenges(), getConfig());
        loadChallengeStatus();
        commandManager.registerCommand(new TimerCommand());
        settingsManager = new SettingsManager(getConfig());
        SettingsMenu.register();
        commandManager.getCommandCompletions().registerAsyncCompletion("settings", context ->
                settingsManager.getSettings());
        commandManager.getCommandCompletions().registerAsyncCompletion("challenges", context ->
                challengesManager.challenges().stream().collect(ArrayList::new, (list, challenge) -> list.add(challenge.getName()), List::addAll));
        commandManager.registerCommand(new SettingsCommand());
        commandManager.registerCommand(new StartCommand());
        commandManager.registerCommand(new ChallengeSpecificCommands());
        commandManager.getCommandCompletions().registerAsyncCompletion("materials", context ->
                Stream.of(Material.values()).collect(ArrayList::new, (list, material) -> list.add(material.name()), List::addAll));
    }

    @Override
    public void onDisable() {
        saveChallengeStatus();
        saveChallenges();
        saveSettings();
        saveTimer();
        saveConfig();
        instance = null;
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    private List<Challenge> registerChallenges() {
        return List.of(
                new AllItemsChallenge(),
                new KillWitherChallenge(),
                new KillEnderDragonChallenge(),
                new NoCraftingTableChallenge()
        );
    }

    private void loadTimer() {
        if (getConfig().get("timer.time") != null) {
            timerManager = new TimerManager(getConfig().getInt("timer.time"));
        } else {
            timerManager = new TimerManager();
        }

        if (getConfig().get("timer.running") != null) {
            if (getConfig().getBoolean("timer.running")) {
                timerManager.start();
            }
        }
    }

    private void saveTimer() {
        getConfig().set("timer.running", timerManager.isRunning());
        timerManager.stop();
        getConfig().set("timer.time", timerManager.getTime());
    }

    private void saveChallenges() {
        challengesManager.challenges().forEach(challenge -> getConfig().set("challenges." + challenge.getName(), challengesManager.isActive(challenge)));
    }

    private void saveSettings() {
        settingsManager.getSettings().forEach(setting -> {
            if (setting.equals("challenge-over")) return;
            getConfig().set("settings." + setting, settingsManager.getSetting(setting));
        });
    }

    private void saveChallengeStatus() {
        if (challengesManager.isActive(challengesManager.getChallange("AllItems"))) {
            getConfig().set("items-collected", challengesManager.getChallange("AllItems", AllItemsChallenge.class)
                    .getItemsCollected().stream().map(Enum::name).toList());
        }
    }

    private void loadChallengeStatus() {
        if (getConfig().get("items-collected") != null) {
            List<String> itemsCollected = getConfig().getStringList("items-collected");
            AllItemsChallenge allItemsChallenge = challengesManager.getChallange("AllItems", AllItemsChallenge.class);
            itemsCollected.forEach(material -> allItemsChallenge.getItemsCollected().add(Material.valueOf(material)));
        }
    }
}
