package main.java.me.cuebyte.nexus.commands;

import main.java.me.cuebyte.nexus.customized.NexusChannel;
import main.java.me.cuebyte.nexus.customized.NexusChannels;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.utils.PermissionsUtils;
import main.java.me.cuebyte.nexus.utils.TextUtils;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandChannelJoin {

	public CommandChannelJoin(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.GOLD, "Usage: ", TextColors.GRAY, "/channel join <channel>")); return; }
	
		String channel = args[1].toLowerCase();
		NexusChannel c = NexusChannels.get(channel);
		
		if(c == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PermissionsUtils.has(sender, "nexus.channel.join." + channel)) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not have permissions to join this channel!"));
			return;
		}
		
		Player player = (Player) sender;
		NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
		
		if(p.getChannel().equalsIgnoreCase(channel)) {
			sender.sendMessage(Text.of(TextColors.RED, "You are already talking in this channel!"));
			return;
		}
		
		p.setChannel(channel);
		p.update();
		
		sender.sendMessage(Text.of(TextColors.GRAY, "Joined channel: ", TextColors.WHITE, TextUtils.color(c.getName())));
		
	}

}
