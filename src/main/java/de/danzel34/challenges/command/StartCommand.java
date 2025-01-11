package de.danzel34.challenges.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import de.danzel34.challenges.Challenges;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

@CommandAlias("start")
public class StartCommand extends BaseCommand {

    @Default
    public void onStart(CommandSender sender) {
        sender.sendMessage(Component.text("The Challenge started!").color(NamedTextColor.GREEN));
        Challenges.getInstance().getTimerManager().start();
    }
}
