package com.dreamless.laithorntools.hoe;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class HoeListener implements Listener {
	
	/*********************
	 * Repairs
	 ********************/
	public static int REPAIR_RATE = 50;
	public static int REPAIR_EXP_GAIN = 3;

	
	private boolean isHoe(ItemStack item) {
		switch(item.getType()) {
		case WOODEN_HOE:
		case STONE_HOE:
		case IRON_HOE:
		case GOLDEN_HOE:
		case DIAMOND_HOE:
			return true;
		default:
			return false;
		}
	}
}
