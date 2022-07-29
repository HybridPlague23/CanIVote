package me.hybridplague.canivote;

import java.text.SimpleDateFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class AskCommand implements CommandExecutor {
	
	public String playTime = "";
	
	public AskCommand(CanIVote main) {
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		
		setPlayTime(p);
		
		p.sendMessage("Fetching data...");
		
		Bukkit.getScheduler().runTaskLater(CanIVote.getPlugin(CanIVote.class), new Runnable() {
			public void run() {
				setPlayTime(p);
				
				List<String> msg = CanIVote.getPlugin(CanIVote.class).getConfig().getStringList("message");

				SimpleDateFormat sd = new SimpleDateFormat(CanIVote.getPlugin(CanIVote.class).getConfig().getString("joindate-format"));
				
				for (String l : msg) {
					
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', l.replace("%playtime%", playTime).replace("%joindate%", sd.format(p.getFirstPlayed()))));
					
				}
				p.performCommand("link");
			}
		}, 60);

		
		
		
		return true;
	}
	
	public void setPlayTime(Player p) {
		String placeholder = "%plan_player_time_month%";
		playTime = PlaceholderAPI.setPlaceholders(p, placeholder);
	}
	
}
