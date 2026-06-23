package dev.danzel.challenges;

import co.aikar.commands.PaperCommandManager;
import dev.danzel.challenges.challanges.Challenge;
import dev.danzel.challenges.challanges.challenges.AllItemsChallenge;
import dev.danzel.challenges.challanges.challenges.KillEnderDragonChallenge;
import dev.danzel.challenges.challanges.challenges.KillWitherChallenge;
import dev.danzel.challenges.challanges.challenges.NoCraftingTableChallenge;
import dev.danzel.challenges.command.*;
import dev.danzel.challenges.listener.CraftItemListener;
import dev.danzel.challenges.listener.EntityDamageByEntityListener;
import dev.danzel.challenges.listener.EntityDamageListener;
import dev.danzel.challenges.listener.EntityDeathListener;
import dev.danzel.challenges.listener.EntityDropItemListener;
import dev.danzel.challenges.listener.EntityPickupItemListener;
import dev.danzel.challenges.listener.InventoryClickListener;
import dev.danzel.challenges.listener.PlayerJoinListener;
import dev.danzel.challenges.listener.PlayerMoveListener;
import dev.danzel.challenges.listener.PlayerQuitListener;
import dev.danzel.challenges.manager.ChallengesManager;
import dev.danzel.challenges.manager.SettingsManager;
import dev.danzel.challenges.manager.TimerManager;
import dev.danzel.challenges.menu.ChallengeAllItemsCollectedMenu;
import dev.danzel.challenges.menu.SettingsChallengeMenu;
import dev.danzel.challenges.menu.SettingsGeneralMenu;
import dev.danzel.challenges.menu.SettingsMenu;
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
        registerListener(new SettingsGeneralMenu());
        registerListener(new SettingsChallengeMenu());
        registerListener(new ChallengeAllItemsCollectedMenu());

        loadTimer();

        challengesManager = new ChallengesManager(registerChallenges(), getConfig());

        loadChallengeStatus();

        commandManager.registerCommand(new ChallengeSpecificCommands());
        commandManager.registerCommand(new SettingsCommand());
        commandManager.registerCommand(new StartCommand());
        commandManager.registerCommand(new TestCommand());
        commandManager.registerCommand(new TimerCommand());

        settingsManager = new SettingsManager(getConfig());

        ChallengeAllItemsCollectedMenu.register();
        SettingsMenu.register();


        commandManager.getCommandCompletions().registerAsyncCompletion("settings", context ->
                settingsManager.getSettings());

        commandManager.getCommandCompletions().registerAsyncCompletion("challenges", context ->
                challengesManager.challenges().stream().collect(ArrayList::new, (list, challenge) -> list.add(challenge.getName()), List::addAll));

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
