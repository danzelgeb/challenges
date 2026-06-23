package dev.danzel.challenges.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.danzel.challenges.Challenges;
import dev.danzel.challenges.challanges.Challenge;
import dev.danzel.challenges.menu.ChallengeAllItemsCollectedMenu;
import org.bukkit.entity.Player;

@CommandAlias("test")
@CommandPermission("challenge.test")
public class TestCommand extends BaseCommand {

    @Subcommand("itemscollectedmenu")
    public void itemsCollectedMenu(Player player) {
        ChallengeAllItemsCollectedMenu.open(player);
    }

    @Subcommand("enablechallange")
    @Syntax("<challenges>")
    @CommandCompletion("@challenges")
    public void enableChallenge(Player player, String challengeName) {
        Challenge challenge = Challenges.getInstance().getChallengesManager().getChallange(challengeName);
        Challenges.getInstance().getChallengesManager().setActive(challenge, true);
        player.sendMessage("Enabled " + challenge.getName());
        player.sendMessage("Active challenges: " + Challenges.getInstance().getChallengesManager().getActiveChallenges().size());
    }
}
