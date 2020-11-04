package com.dreamless.laithorntools.hoe;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.dreamless.laithorn.LaithornUtils;
import com.dreamless.laithorn.api.Fragment;
import com.dreamless.laithorn.api.LaithornRegister;
import com.dreamless.laithorn.api.RecipeType;
import com.dreamless.laithorntools.LaithornTools;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;

public class HoeToolRecipe {

	protected static HashMap<Material, Integer> toolUseCostMap = new HashMap<Material, Integer>();
	protected static HashMap<Material, Integer> toolUseExpMap = new HashMap<Material, Integer>(); 
	
	public static void registerRecipes(FileConfiguration currentConfig) {

		/**
		 * Crafting
		 */

		// Wood
		LaithornRegister.registerItemCrafting(itemFactory(Material.WOODEN_HOE), Material.WOODEN_HOE,
				currentConfig.getInt("WOODEN_HOE_create_level", 0), currentConfig.getInt("WOODEN_HOE_create_exp", 10),
				RecipeType.CENTERED, null, "magicked_wooden_hoe", LaithornTools.tools);

		// Stone
		LaithornRegister.registerItemCrafting(itemFactory(Material.STONE_HOE), Material.STONE_HOE,
				currentConfig.getInt("STONE_HOE_create_level", 5), currentConfig.getInt("STONE_HOE_create_exp", 20),
				RecipeType.CENTERED, null, "magicked_stone_hoe", LaithornTools.tools);

		// Wood
		LaithornRegister.registerItemCrafting(itemFactory(Material.IRON_HOE), Material.IRON_HOE,
				currentConfig.getInt("IRON_HOE_create_level", 10), currentConfig.getInt("IRON_HOE_create_exp", 40),
				RecipeType.CENTERED, null, "magicked_iron_hoe", LaithornTools.tools);

		// Wood
		LaithornRegister.registerItemCrafting(itemFactory(Material.GOLDEN_HOE), Material.GOLDEN_HOE,
				currentConfig.getInt("GOLDEN_HOE_create_level", 15), currentConfig.getInt("GOLDEN_HOE_create_exp", 80),
				RecipeType.CENTERED, null, "magicked_golden_hoe", LaithornTools.tools);

		// Wood
		LaithornRegister.registerItemCrafting(itemFactory(Material.DIAMOND_HOE), Material.DIAMOND_HOE,
				currentConfig.getInt("DIAMOND_HOE_create_level", 20),
				currentConfig.getInt("DIAMOND_HOE_create_exp", 160), RecipeType.CENTERED, null, "magicked_diamond_hoe",
				LaithornTools.tools);

		/**
		 * Repairing
		 */

		// Wood
		LaithornRegister.registerItemRepair(Material.WOODEN_HOE, currentConfig.getInt("WOODEN_HOE_create_level", 0),
				currentConfig.getInt("WOODEN_HOE_repair_exp", 3), currentConfig.getInt("WOODEN_HOE_repair_rate", 20), true, null);

		// Stone
		LaithornRegister.registerItemRepair(Material.STONE_HOE, currentConfig.getInt("STONE_HOE_create_level", 5),
				currentConfig.getInt("STONE_HOE_repair_exp", 3), currentConfig.getInt("STONE_HOE_repair_rate", 20), true, null);

		// Iron
		LaithornRegister.registerItemRepair(Material.IRON_HOE, currentConfig.getInt("IRON_HOE_create_level", 10),
				currentConfig.getInt("IRON_HOE_repair_exp", 3), currentConfig.getInt("IRON_HOE_repair_rate", 20), true, null);
		
		// Gold
		LaithornRegister.registerItemRepair(Material.GOLDEN_HOE, currentConfig.getInt("GOLDEN_HOE_create_level", 15),
				currentConfig.getInt("GOLDEN_HOE_repair_exp", 7), currentConfig.getInt("GOLDEN_HOE_repair_rate", 20), true, null);
		
		// Diamond
		LaithornRegister.registerItemRepair(Material.DIAMOND_HOE, currentConfig.getInt("DIAMOND_HOE_create_level", 20),
				currentConfig.getInt("DIAMOND_HOE_repair_exp", 3), currentConfig.getInt("DIAMOND_HOE_repair_rate", 20), true, null);
	}
	
	public static void setUseCostAndExpGain(FileConfiguration currentConfig) {
		/**
		 * Use Cost
		 */
		
		toolUseCostMap.put(Material.WOODEN_HOE, currentConfig.getInt("WOODEN_HOE_cost", 0));
		toolUseCostMap.put(Material.STONE_HOE, currentConfig.getInt("STONE_HOE_cost", 1));
		toolUseCostMap.put(Material.IRON_HOE, currentConfig.getInt("IRON_HOE_cost", 0));
		toolUseCostMap.put(Material.GOLDEN_HOE, currentConfig.getInt("GOLDEN_HOE_cost", 0));
		toolUseCostMap.put(Material.DIAMOND_HOE, currentConfig.getInt("DIAMOND_HOE_cost", 9));
		
		/**
		 * Exp Gain
		 */
		
		toolUseExpMap.put(Material.WOODEN_HOE, currentConfig.getInt("WOODEN_HOE_attunement_exp", 5));
		toolUseExpMap.put(Material.STONE_HOE, currentConfig.getInt("STONE_HOE_attunement_exp", 5));
		toolUseExpMap.put(Material.IRON_HOE, currentConfig.getInt("IRON_HOE_attunement_exp", 5));
		toolUseExpMap.put(Material.GOLDEN_HOE, currentConfig.getInt("GOLDEN_HOE_attunement_exp", 15));
		toolUseExpMap.put(Material.DIAMOND_HOE, currentConfig.getInt("GOLDEN_HOE_attunement_exp", 5));
	}

	private static ItemStack itemFactory(Material material) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();

		// ItemMeta
		itemMeta.setDisplayName(com.dreamless.laithorn.LanguageReader.getText(material.name() + "_Name"));
		itemMeta.setLore(LaithornUtils.wrapText(com.dreamless.laithorn.LanguageReader.getText(material.name() + "_Text")));
		item.setItemMeta(itemMeta);

		// NBT
		NBTItem nbti = new NBTItem(item);
		NBTCompound laithorn = nbti.addCompound(Fragment.getTopLevelTag());
		laithorn.setString("module", "LaithornTools");
		item = nbti.getItem();

		return item;
	}
}
