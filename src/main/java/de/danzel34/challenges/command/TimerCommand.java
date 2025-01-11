package de.danzel34.challenges.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import de.danzel34.challenges.Challenges;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

@CommandAlias("timer")
@CommandPermission("challenges.timer")
public class TimerCommand extends BaseCommand {

    @Subcommand("start")
    public void startTimer(CommandSender sender) {
        if (Challenges.getInstance().getTimerManager().isRunning()) {
            sender.sendMessage(Component.text("The timer is already running!").color(NamedTextColor.RED));
            return;
        }

        Challenges.getInstance().getTimerManager().start();
        sender.sendMessage(Component.text("The timer is now running.").color(NamedTextColor.GREEN));
     }

    @Subcommand("stop")
    public void stopTimer(CommandSender sender) {
        if (!Challenges.getInstance().getTimerManager().isRunning()) {
            sender.sendMessage(Component.text("The timer is already stopped!").color(NamedTextColor.RED));
            return;
        }

        Challenges.getInstance().getTimerManager().stop();
        sender.sendMessage(Component.text("The timer is now stopped.").color(NamedTextColor.GREEN));
    }

}
