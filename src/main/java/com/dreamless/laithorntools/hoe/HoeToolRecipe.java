package com.dreamless.laithorntools.hoe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import com.dreamless.laithorntools.LaithornTools;

public class HoeToolRecipe {

	public static final String WOODEN_HOE_REPAIR_STRING = "wooden_hoe_repair";
	public static final String WOODEN_HOE_CREATE_STRING = "wooden_hoe_create";
	public static final String STONE_HOE_REPAIR_STRING = "stone_hoe_repair";
	public static final String STONE_HOE_CREATE_STRING = "stone_hoe_create";
	public static final String IRON_HOE_REPAIR_STRING = "iron_hoe_repair";
	public static final String IRON_HOE_CREATE_STRING = "iron_hoe_create";
	public static final String GOLDEN_HOE_REPAIR_STRING = "gold_hoe_repair";
	public static final String GOLDEN_HOE_CREATE_STRING = "gold_hoe_create";
	public static final String DIAMOND_HOE_REPAIR_STRING = "diamond_hoe_repair";
	public static final String DIAMOND_HOE_CREATE_STRING = "diamond_hoe_create";

	public static void registerRecipes() {
		Bukkit.addRecipe(hoeRecipeWood());
		Bukkit.addRecipe(hoeRecipeStone());
		Bukkit.addRecipe(hoeRecipeIron());
		Bukkit.addRecipe(hoeRecipeGold());
		Bukkit.addRecipe(hoeRecipeDiamond());
	}

	private static ShapedRecipe hoeRecipeWood() {
		NamespacedKey key = new NamespacedKey(LaithornTools.tools, "magicked_hoe_wood");

		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.WOODEN_HOE));

		recipe.shape("EEE", "ESE", "EEE");
		recipe.setIngredient('E', Material.FLINT);
		recipe.setIngredient('S', Material.WOODEN_HOE);

		return recipe;
	}

	private static ShapedRecipe hoeRecipeStone() {
		NamespacedKey key = new NamespacedKey(LaithornTools.tools, "magicked_hoe_stone");

		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.STONE_HOE));

		recipe.shape("EEE", "ESE", "EEE");
		recipe.setIngredient('E', Material.FLINT);
		recipe.setIngredient('S', Material.STONE_HOE);

		return recipe;
	}

	private static ShapedRecipe hoeRecipeIron() {
		NamespacedKey key = new NamespacedKey(LaithornTools.tools, "magicked_hoe_iron");

		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.IRON_HOE));

		recipe.shape("EEE", "ESE", "EEE");
		recipe.setIngredient('E', Material.FLINT);
		recipe.setIngredient('S', Material.IRON_HOE);

		return recipe;
	}

	private static ShapedRecipe hoeRecipeGold() {
		NamespacedKey key = new NamespacedKey(LaithornTools.tools, "magicked_hoe_golden");

		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.GOLDEN_HOE));

		recipe.shape("EEE", "ESE", "EEE");
		recipe.setIngredient('E', Material.FLINT);
		recipe.setIngredient('S', Material.GOLDEN_HOE);

		return recipe;
	}

	private static ShapedRecipe hoeRecipeDiamond() {
		NamespacedKey key = new NamespacedKey(LaithornTools.tools, "magicked_hoe_wood");

		ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.DIAMOND_HOE));

		recipe.shape("EEE", "ESE", "EEE");
		recipe.setIngredient('E', Material.FLINT);
		recipe.setIngredient('S', Material.DIAMOND_HOE);

		return recipe;
	}

	public static String getRepairString(ItemStack item) {
		switch (item.getType()) {
		case WOODEN_HOE:
			return WOODEN_HOE_REPAIR_STRING;
		case STONE_HOE:
			return STONE_HOE_REPAIR_STRING;
		case IRON_HOE:
			return IRON_HOE_REPAIR_STRING;
		case GOLDEN_HOE:
			return GOLDEN_HOE_REPAIR_STRING;
		case DIAMOND_HOE:
			return DIAMOND_HOE_REPAIR_STRING;
		default:
			return null;
		}
	}
	
	public static String getCreateString(ItemStack item) {
		switch (item.getType()) {
		case WOODEN_HOE:
			return WOODEN_HOE_CREATE_STRING;
		case STONE_HOE:
			return STONE_HOE_CREATE_STRING;
		case IRON_HOE:
			return IRON_HOE_CREATE_STRING;
		case GOLDEN_HOE:
			return GOLDEN_HOE_CREATE_STRING;
		case DIAMOND_HOE:
			return DIAMOND_HOE_CREATE_STRING;
		default:
			return null;
		}
	}
}
