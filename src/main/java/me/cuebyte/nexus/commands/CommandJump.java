package main.java.me.cuebyte.nexus.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.cuebyte.nexus.utils.PermissionsUtils;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.World;


public class CommandJump implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "nexus.jump")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		Player player = (Player) sender;
		BlockRay<World> r = BlockRay.from(player).blockLimit(300).build();
		BlockRayHit<World> h = null;

		while(r.hasNext()) {
			BlockRayHit<World> c = r.next();
			if(player.getWorld().getBlockType(c.getBlockPosition()).equals(BlockTypes.AIR)) continue;
			h = c; break;
		}

		if(h == null) {
			sender.sendMessage(Text.of(TextColors.RED, "No block found!"));
			return CommandResult.success();
		}

		player.setLocationSafely(h.getLocation());

		sender.sendMessage(Text.of(TextColors.GRAY, "Jumped to ", TextColors.GOLD, "x:", h.getBlockX(), " y:", h.getBlockY(), " z:", h.getBlockZ()));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /jump").color(TextColors.GOLD).build();
	private final Text help = Text.builder("Help: /jump").color(TextColors.GOLD).build();
	private final Text description = Text.builder("Nexus | Jump Command").color(TextColors.GOLD).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";

	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
