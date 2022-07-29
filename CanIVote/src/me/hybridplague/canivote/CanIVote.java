package me.hybridplague.canivote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class CanIVote extends JavaPlugin {

	public static List<Inventory> invs = new ArrayList<Inventory>();
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new SeenCommand(), this);
		this.getCommand("canivote").setExecutor(new AskCommand(this));
		this.getCommand("bcseen").setExecutor(new SeenCommand());
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	
	
}
