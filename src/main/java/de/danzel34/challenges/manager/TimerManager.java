package de.danzel34.challenges.manager;

import de.danzel34.challenges.Challenges;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager {
    @Getter
    private int time;
    @Getter
    private boolean running;
    private BukkitRunnable runnable;

    public TimerManager() {
        this.time = 0;
    }

    public TimerManager(int time) {
        this.time = time;
    }

    public void start() {
        if (running) return;
        this.running = true;
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendActionBar(currentTime());
                }
            }
        };
        runnable.runTaskTimerAsynchronously(Challenges.getInstance(), 0, 20);
    }

    public void stop() {
        if (!running) return;
        this.running = false;
        if (runnable != null)
            runnable.cancel();
    }

    private Component currentTime() {
        int days = time / 86400;
        int hours = (time % 86400) / 3600;
        int minutes = ((time % 86400) % 3600) / 60;
        int seconds = ((time % 86400) % 3600) % 60;
        return Component.text(days + "d:" + hours + "h:" + minutes + "m:" + seconds + "s")
                .color(NamedTextColor.BLUE)
                .decorate(TextDecoration.BOLD);
    }
}
