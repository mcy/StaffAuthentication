package com.xorinc.staffauth;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class PasswordStorage {
	
	private static final String CONFIG_NAME = "passwords.yml";
	private AuthPlugin plugin;
	
	public PasswordStorage(AuthPlugin plugin){
		this.plugin = plugin;
	}
	
	public Map<String, Integer> loadMaxAttemptsSettings(){
		
		File datafolder = plugin.getDataFolder();
		
    	if (!datafolder.exists()) {
    		datafolder.mkdirs();
        }
    	
        File f = new File(datafolder, CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME);
                return null;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('.');
        conf.options().header(new StringBuilder().append("Password storage and configuration.").append(System.getProperty("line.separator")).toString());
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return null;
        }
		
        Map<String, Integer> data = new HashMap<String, Integer>();
        
        if(!conf.contains("maxAttempts") || !conf.isInt("maxAttempts"))
        	conf.set("maxAttempts", 10);
        data.put("maxAttempts", conf.getInt("maxAttempts"));
        
        if(!conf.contains("authLockTime") || !conf.isInt("authLockTime"))
        	conf.set("authLockTime", 30);
        data.put("authLockTime", conf.getInt("authLockTime"));   
    	
    	try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return data;
	}
	
	public Map<String, String> loadPasswords(){
		
		File datafolder = plugin.getDataFolder();
		
    	if (!datafolder.exists()) {
    		datafolder.mkdirs();
        }
    	
        File f = new File(datafolder, CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME);
                return null;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('.');
        conf.options().header(new StringBuilder().append("Password storage.").append(System.getProperty("line.separator")).toString());
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return null;
        }
		
        if(!conf.contains("passwords") || !conf.isConfigurationSection("passwords"))
        	conf.createSection("passwords");
        ConfigurationSection sec = conf.getConfigurationSection("passwords");
        
        Map<String, String> passwords = new HashMap<String, String>();
          
        for(String key : sec.getKeys(false)){       	
        	if(sec.isString(key))
        		passwords.put(key, sec.getString(key));        	
        }
    	
    	try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return passwords;
	}
	
	public void savePasswords(Map<String,String> passwords){
		
		File datafolder = plugin.getDataFolder();
		
    	if (!datafolder.exists()) {
    		datafolder.mkdirs();
        }
    	
        File f = new File(datafolder, CONFIG_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to create " + CONFIG_NAME);
                return;
            }
        }
        
        YamlConfiguration conf = new YamlConfiguration();
        conf.options().pathSeparator('.');
        conf.options().header(new StringBuilder().append("Password storage.").append(System.getProperty("line.separator")).toString());
        
        try {
            conf.load(f);
        } catch (Exception e) {
            plugin.getLogger().severe("==================== " + plugin.getDescription().getName() + " ====================");
            plugin.getLogger().severe("Unable to load " + CONFIG_NAME);
            plugin.getLogger().severe("Check your config for formatting issues!");
            plugin.getLogger().severe("Error: " + e.getMessage());
            plugin.getLogger().severe("====================================================");
            return;
        }
		
        conf.createSection("passwords", passwords);
    	
    	try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }    	
	}
}
