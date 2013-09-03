package com.xorinc.staffauth;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthCommand implements CommandExecutor{

	private AuthPlugin plugin;
	
	public AuthCommand(AuthPlugin plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "The console does not require further authentication.");
			return true;
		}
		
		if(args.length < 1){
			sender.sendMessage(ChatColor.RED + "Please enter your password.");
			return true;
		}
		
		if(!plugin.passwords.containsKey(sender.getName())){
			
			plugin.passwords.put(sender.getName(), args[0]);
			plugin.io.savePasswords(plugin.passwords);
			plugin.isAuthed.put(sender.getName(), true);
			
			sender.sendMessage(ChatColor.GOLD + "You have created a password. Your password is: " + ChatColor.ITALIC + args[0] + ChatColor.GOLD + 
					". If this is not the password you want, or wish to at any point change your password, please speak to the server administrator.");
			return true;
		}
		else{
			if(plugin.isAuthed.get(sender.getName())){
				sender.sendMessage(ChatColor.GOLD + "You are already authenticated.");
				return true;
			}
			else{
				if(plugin.isLocked.containsKey(sender.getName()) && plugin.isLocked.get(sender.getName())){
					sender.sendMessage(ChatColor.DARK_RED + "You have failed to authenticate, and must wait before being able to authenticate again.");
					return true;
				}
							
				if(plugin.passwords.get(sender.getName()).equals(args[0])){
					plugin.isAuthed.put(sender.getName(), true);
					sender.sendMessage(ChatColor.GOLD + "You have been successfully authenticated.");
					return true;
				}
				else{
					plugin.attempts.put(sender.getName(), plugin.attempts.get(sender.getName()) + 1);
					sender.sendMessage(ChatColor.DARK_RED + "Incorrect password. Authentication failed. You have " + (plugin.maxAttempts - plugin.attempts.get(sender.getName())) + " attempts left.");
					
					if(plugin.attempts.get(sender.getName()) >= plugin.maxAttempts){
						
						((Player) sender).kickPlayer("Authentication failed. You may try again in " + plugin.authLockTime + " minutes.");
						
						plugin.getLogger().warning("Player " + sender.getName() + " failed to authenticate!");
						
						plugin.isLocked.put(sender.getName(), true);
						
						final String name = sender.getName();
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

							@Override
							public void run() {
								plugin.isLocked.put(name, false);
								
							}
							
						}, plugin.authLockTime * 60 * 20);
					}
					
					return true;
				}
			}
			
		}
	}
	
	
	
}
