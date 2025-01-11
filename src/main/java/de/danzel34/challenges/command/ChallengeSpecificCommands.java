package de.danzel34.challenges.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import de.danzel34.challenges.Challenges;
import de.danzel34.challenges.challanges.challenges.AllItemsChallenge;
import de.danzel34.challenges.manager.ChallengesManager;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@CommandAlias("specific")
public class ChallengeSpecificCommands extends BaseCommand {

    @Subcommand("skipitem")
    @CommandCompletion("@materials")
    @Syntax("<materials>")
    public void skipItem(CommandSender sender, String material) {
        ChallengesManager challengesManager = Challenges.getInstance().getChallengesManager();
        if (!challengesManager.isActive(challengesManager.getChallange("AllItems"))) {
            sender.sendMessage("§cThis command can only be used in the AllItems challenge");
            return;
        }

        challengesManager.getChallange("AllItems", AllItemsChallenge.class).getItemsCollected().add(Material.valueOf(material));
        sender.sendMessage("§aSuccessfully skipped item " + material);
    }

    @Subcommand("collecteditems")
    public void collectedItems(CommandSender sender) {
        ChallengesManager challengesManager = Challenges.getInstance().getChallengesManager();
        if (!challengesManager.isActive(challengesManager.getChallange("AllItems"))) {
            sender.sendMessage("§cThis command can only be used in the AllItems challenge");
            return;
        }

        sender.sendMessage("§aCollected items: ");
        for (Material material : challengesManager.getChallange("AllItems", AllItemsChallenge.class).getItemsCollected()) {
            sender.sendMessage("§7- " + material.name());
        }
    }

    @Subcommand("getgoal")
    @Syntax("<name>")
    public void getGoal(CommandSender sender, String name) {
        ChallengesManager challengesManager = Challenges.getInstance().getChallengesManager();
        sender.sendMessage("§aGoal: " + challengesManager.getChallange(name).challengeGoal().name());
    }
}
