package de.danzel34.challenges.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.Challenge;
import de.danzel34.challenges.menu.SettingsMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("settings")
@CommandPermission("challenges.settings")
public class SettingsCommand extends BaseCommand {

    @Default
    public void onDeafult(Player player) {
        SettingsMenu.open(player);
    }

    @Subcommand("setting")
    @CommandCompletion("@settings true|false")
    @Syntax("<setting> [true|false]")
    public void changeSetting(CommandSender sender, String setting, @Optional String value) {
        if (!Challenges.getInstance().getSettingsManager().getSettings().contains(setting)) {
            sender.sendMessage(Component.text("The setting you want to change was not found!").color(NamedTextColor.RED));
            return;
        }

        if (value == null) {
            sender.sendMessage(Component.text("The setting " + setting + " has the value " + Challenges.getInstance().getSettingsManager().getSetting(setting)));
            return;
        }

        Challenges.getInstance().getSettingsManager().setSetting(setting, Boolean.valueOf(value));
        sender.sendMessage(Component.text("Set " + setting + " to " + value + "."));
    }

    @Subcommand("challenges")
    @CommandCompletion("@challenges true|false")
    @Syntax("<challenge> [true|false]")
    public void listChallenges(CommandSender sender, @Optional String challengeName, @Optional String value) {
        if (challengeName == null) {

            sender.sendMessage(Component.text("Challenges:"));
            Challenges.getInstance().getChallengesManager().challenges().forEach(challenges ->
                    sender.sendMessage(Component.text("- " + challenges.getName())));
        } else {
            Challenge challenge = Challenges.getInstance().getChallengesManager().getChallange(challengeName);

            if (challenge == null) {
                sender.sendMessage(Component.text("This challenge is not found!").color(NamedTextColor.RED));
                return;
            }

            if (value != null && !value.equals("true") && !value.equals("false")) {
                sender.sendMessage(Component.text("The value must be true or false or not given!").color(NamedTextColor.RED));
                return;
            }

            boolean active = Challenges.getInstance().getChallengesManager().isActive(challenge);

            if (value == null) {
                sender.sendMessage(Component.text("The challenge " + challengeName + " is " + (active ? "active." : "inactive.")));
                return;
            }

            boolean activeValue = Boolean.parseBoolean(value);

            Challenges.getInstance().getChallengesManager().setActive(challenge, activeValue);
            sender.sendMessage(Component.text("Set " + challengeName + " to " + activeValue + "."));
        }
    }
}
