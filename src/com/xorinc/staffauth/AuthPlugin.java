package com.xorinc.staffauth;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin{
	
	PasswordStorage io = new PasswordStorage(this);
	Map<String, String> passwords = new HashMap<String, String>();
	Map<String, Boolean> isAuthed = new HashMap<String, Boolean>();
	Map<String, Integer> attempts = new HashMap<String, Integer>();
	Map<String, Boolean> isLocked = new HashMap<String, Boolean>();
	int maxAttempts, authLockTime;
		
	public void onEnable(){
		passwords = io.loadPasswords();
		
		Map<String, Integer> temp = io.loadMaxAttemptsSettings();		
		maxAttempts = temp.get("maxAttempts");
		authLockTime = temp.get("authLockTime");
		
		getCommand("authreload").setExecutor(new CommandExecutor(){
			
			@Override
			public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {					
				passwords = io.loadPasswords();
				Map<String, Integer> temp = io.loadMaxAttemptsSettings();		
				maxAttempts = temp.get("maxAttempts");
				authLockTime = temp.get("authLockTime");
				
				sender.sendMessage(ChatColor.GOLD + "Settings reloaded.");		
				return true;		
			}
			
		});
		getCommand("authenticate").setExecutor(new AuthCommand(this));
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	public void onDisable(){
		io.savePasswords(passwords);
	}
	
	
}
