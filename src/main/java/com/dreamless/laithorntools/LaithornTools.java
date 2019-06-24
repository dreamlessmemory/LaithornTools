package com.dreamless.laithorntools;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.dreamless.laithorntools.hoe.HoeListener;
import com.mysql.jdbc.Connection;

public class LaithornTools extends JavaPlugin{
	public static LaithornTools tools;

	// Connection vars
	public static Connection connection; // This is the variable we will use to connect to database

	// DataBase vars.
	private String username;
	private String password;
	private String url;
	private static String database;
	private static String testdatabase;
	
	// Listeners
	//private CommandListener commandListener;
	private HoeListener hoeListener;

	// debug
	public static boolean debug;
	public static boolean development;
	
	//Language
	public LanguageReader languageReader;
	
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
		
		// Load data
		//DataHandler.loadWellArea();


		// SQL Setup
		try { // We use a try catch to avoid errors, hopefully we don't get any.
			Class.forName("com.mysql.jdbc.Driver"); // this accesses Driver in jdbc.
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("jdbc driver unavailable!");
			return;
		}
		try {
			connection = (Connection) DriverManager.getConnection(url, username, password);
			connection.setAutoReconnect(true);
		} catch (SQLException e) { // catching errors)
			e.printStackTrace(); // prints out SQLException errors to the console (if any)
		}
		
		// Load Cache

		// Listeners
		hoeListener = new HoeListener();
		
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
		
		tools.getServer().getPluginManager().registerEvents(new PlayerListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new WellListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new MobDeathListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new BlockBreakListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new FishingListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new GrindstoneListener(), tools);
		tools.getServer().getPluginManager().registerEvents(new InventoryListener(), tools);
		
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

		// Disable Server
		try { // using a try catch to catch connection errors (like wrong sql password...)
			if (connection != null && !connection.isClosed()) { // checking if connection isn't null to
				// avoid receiving a nullpointer
				connection.close(); // closing the connection field variable.
			}
		} catch (Exception e) {
			e.printStackTrace();
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

		// Database settings
		username = currentConfig.getString("username");
		password = currentConfig.getString("password");
		url = currentConfig.getString("url");
		database = currentConfig.getString("prefix");
		testdatabase = currentConfig.getString("testprefix");

		// Dev/Debug control
		debug = currentConfig.getBoolean("debug", false);
		development = currentConfig.getBoolean("development", false);
		
		// Effects
		// Balancing
		
		
		
	
		/*** text.yml ***/
		currentFile = new File(tools.getDataFolder(), "text.yml");
		if (!currentFile.exists()) {
			return false;
		}
		
		LanguageReader.loadEntries(currentFile);
		
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

	public static String getDatabase() {
			return development ? testdatabase : database;

	}
}
