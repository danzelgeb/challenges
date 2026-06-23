package dev.danzel.challenges.manager;

import dev.danzel.challenges.Challenges;
import dev.danzel.challenges.utils.Txt;
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
    private BukkitRunnable timerRunnable;
    private BukkitRunnable actionBarRunnable;

    public TimerManager() {
        this.time = 0;
    }

    public TimerManager(int time) {
        this.time = time;
    }

    public void start() {
        if (running) return;
        this.running = true;
        if (actionBarRunnable != null) actionBarRunnable.cancel();
        timerRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendActionBar(currentTime());
                }
            }
        };
        timerRunnable.runTaskTimerAsynchronously(Challenges.getInstance(), 0, 20);
    }

    public void stop() {
        if (!running) return;
        this.running = false;
        if (timerRunnable != null)
            timerRunnable.cancel();

        actionBarRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendActionBar(Txt.text("Timer is paused!"));
                }
            }
        };
        actionBarRunnable.runTaskTimerAsynchronously(Challenges.getInstance(), 0, 20);
    }

    public void reset() {
        stop();
        this.time = 0;
        actionBarRunnable.cancel();
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
