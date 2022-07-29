package me.hybridplague.canivote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class SeenCommand implements CommandExecutor, Listener {

	public String playTime = "";
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;

		if (args.length == 0) {
			p.sendMessage("/bcseen <player>");
			return true;
		} else {
			if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() || Bukkit.getOfflinePlayer(args[0]).isOnline()) {
				OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
				
				setPlayTime(t);
				p.sendMessage("Fetching data...");
				
				Bukkit.getScheduler().runTaskLater(CanIVote.getPlugin(CanIVote.class), new Runnable() {
					public void run() {
						setPlayTime(t);
						
						openInv(p, t);
						
					}
				}, 15);
				
				
				
			}
		}
		
		
		return true;
	}
	
	public void setPlayTime(OfflinePlayer p) {
		String placeholder = "%plan_player_time_month%";
		playTime = PlaceholderAPI.setPlaceholders(p, placeholder);
	}
	
	public Inventory inv;
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (CanIVote.invs.contains(e.getInventory())) {
			e.setCancelled(true);
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void openInv(Player p, OfflinePlayer t) {
		
		inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "BusinessCraft NEW Seen");
		CanIVote.invs.add(inv);
		
		p.openInventory(inv);
		
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("MMM-dd-yyyy");
		
		
		meta.setOwner(t.getName());
		meta.setDisplayName(ChatColor.GREEN + t.getName() + "'s PlayTime");
		if (t.getName().equals("_Ascended_")) {
			lore.add("");
			lore.add(ChatColor.RED + "This user smells bad, so we will not get their information.");
		} else {
			lore.add("");
			lore.add(ChatColor.RED + "PlayTime - 30 Days: " + ChatColor.GRAY + playTime);
			lore.add(ChatColor.RED + "Current Date: " + ChatColor.GRAY + sd.format(date));
		}
		
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		inv.setItem(4, item);
		
	}
	
}
