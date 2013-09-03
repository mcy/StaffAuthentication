package com.xorinc.staffauth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	private AuthPlugin plugin;
	
	public PlayerListener(AuthPlugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		if(!player.hasPermission("staffauth.auth"))
			return;
		
		plugin.isAuthed.put(player.getName(), false);	
		plugin.attempts.put(player.getName(), 0);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onPlayerQuit(PlayerQuitEvent event){
		
		plugin.isAuthed.remove(event.getPlayer().getName());
		
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onPlayerKick(PlayerKickEvent event){
		
		plugin.isAuthed.remove(event.getPlayer().getName());
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		String label = event.getMessage().split(" ")[0].substring(1);
		
		if(label.equalsIgnoreCase("authenticate") || label.equalsIgnoreCase("auth"))
			return;
		
		event.setMessage("/");
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setMessage("");
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onAsyncPlayerChat(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBlockBreak(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onBlockPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onInventoryOpen(InventoryOpenEvent event){
		
		if(!(event.getPlayer() instanceof Player))
			return;
		
		Player player = (Player) event.getPlayer();
		
		if(plugin.isAuthed.containsKey(player.getName()) && plugin.isAuthed.get(player.getName()))
			return;
		
		event.setCancelled(true);
		player.sendMessage("" + ChatColor.RED + ChatColor.ITALIC + "Please authenticate yourself with /authenticate.");
		
	}
}
