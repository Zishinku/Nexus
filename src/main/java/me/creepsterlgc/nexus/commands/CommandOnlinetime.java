package main.java.me.creepsterlgc.nexus.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.nexus.customized.NexusDatabase;
import main.java.me.creepsterlgc.nexus.customized.NexusPlayer;
import main.java.me.creepsterlgc.nexus.utils.PermissionsUtils;
import main.java.me.creepsterlgc.nexus.utils.TimeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandOnlinetime implements CommandCallable {

	public Game game;

	public CommandOnlinetime(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "nexus.onlinetime")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/onlinetime [player]")); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) {

			if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

			Player player = (Player)sender;
			NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
			sender.sendMessage(Text.of(TextColors.GRAY, "Your onlinetime: ", TextColors.GOLD, TimeUtils.toString(p.getOnlinetime())));

			return CommandResult.success();

		}

		NexusPlayer player = NexusDatabase.getPlayer(NexusDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		sender.sendMessage(Text.of(TextColors.GRAY, player.getName(), "'s onlinetime: ", TextColors.GOLD, TimeUtils.toString(player.getOnlinetime())));

		return CommandResult.success();

	}

	 @Override
	public Text getUsage(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getHelp(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 @Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 @Override
	public boolean testPermission(CommandSource sender) { return false; }

}