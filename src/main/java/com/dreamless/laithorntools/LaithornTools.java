package com.dreamless.laithorntools;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.dreamless.laithorntools.hoe.HoeToolListener;
import com.dreamless.laithorntools.hoe.HoeToolRecipe;

public class LaithornTools extends JavaPlugin{
	public static LaithornTools tools;
	
	// Listeners
	//private CommandListener commandListener;
	// debug
	public static boolean debug;
	public static boolean development;
	
	//Language
	public com.dreamless.laithorn.LanguageReader languageReader;
	
	@Override
	public void onEnable() {

		tools = this;

		// Load Config
		try {
			if (!readConfig()) {
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		// Listeners
		tools.getServer().getPluginManager().registerEvents(new HoeToolListener(), tools);
		
		/*
		commandListener = new CommandListener();
		
		getCommand("givefragment").setExecutor(commandListener);
		getCommand("fragments").setExecutor(commandListener);
		getCommand("clearwell").setExecutor(commandListener);
		getCommand("setwell").setExecutor(commandListener);
		getCommand("laithornlevels").setExecutor(commandListener);
		getCommand("attunementlevel").setExecutor(commandListener);
		getCommand("smithinglevel").setExecutor(commandListener);
		getCommand("autopickup").setExecutor(commandListener);
		
		*/
		
		// Runables
		//new CacheHandler.PeriodicCacheSave().runTaskTimer(tools, 3600, 3600);

		PlayerMessager.log(this.getDescription().getName() + " enabled!");
	}

	@Override
	public void onDisable() {

		// Save data
		//CacheHandler.saveCacheToDatabase();
		
		// Disable listeners
		HandlerList.unregisterAll(this);

		// Stop shedulers
		getServer().getScheduler().cancelTasks(this);

		if (tools == null) {
			return;
		}

		PlayerMessager.log(this.getDescription().getName() + " disabled!");

	}
	
	private boolean readConfig() {
		
		/*** config.yml ***/
		File currentFile = new File(tools.getDataFolder(), "config.yml");
		if (!currentFile.exists()) {
			return false;
		}
		FileConfiguration currentConfig = YamlConfiguration.loadConfiguration(currentFile);

		// Dev/Debug control
		debug = currentConfig.getBoolean("debug", true);
		development = currentConfig.getBoolean("development", false);
	
		/*** text.yml ***/
		currentFile = new File(tools.getDataFolder(), "text.yml");
		if (!currentFile.exists()) {
			return false;
		}
		
		com.dreamless.laithorn.LanguageReader.loadEntries(currentFile);
		
		HoeToolRecipe.registerRecipes(currentConfig);
		HoeToolRecipe.setUseCostAndExpGain(currentConfig);
		
		// Continuous

		return true;
	}
	
	public  void reload() {	
		try {
			if (!tools.readConfig()) {
				tools = null;
				getServer().getPluginManager().disablePlugin(this);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			tools = null;
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
	}
}
