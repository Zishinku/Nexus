package main.java.me.cuebyte.nexus.events;

import main.java.me.cuebyte.nexus.Controller;
import main.java.me.cuebyte.nexus.customized.NexusDatabase;
import main.java.me.cuebyte.nexus.customized.NexusPlayer;
import main.java.me.cuebyte.nexus.files.FileConfig;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerMove {

    @Listener
    public void onPlayerMove(DisplaceEntityEvent event) {
    	
    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	
    	if(!FileConfig.AFK_ENABLE_SYSTEM()) return;
    	
    	NexusPlayer p = NexusDatabase.getPlayer(player.getUniqueId().toString());
    	if(p == null) return;
		p.setLastaction(System.currentTimeMillis());
		
		if(p.getAFK()) {
			Controller.broadcast(Text.of(TextColors.GOLD, player.getName(), TextColors.GRAY, " is no longer afk."));
			p.setAFK(false);
		}
		
		NexusDatabase.addPlayer(p.getUUID(), p);
		
    }
	
}
