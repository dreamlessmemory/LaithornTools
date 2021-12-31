package com.dreamless.laithorntools.hoe;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.dreamless.laithorn.PlayerMessager;
import com.dreamless.laithorn.api.Fragment;
import com.dreamless.laithorn.events.PlayerExperienceGainEvent;
import com.dreamless.laithorntools.LaithornTools;
import com.dreamless.laithorn.events.PlayerExperienceVariables.GainType;
import com.dreamless.nbtapi.NBTCompound;
import com.dreamless.nbtapi.NBTItem;

public class HoeToolListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (!isReadyCrop(block)) {
			PlayerMessager.debugLog("[HoeTool] Not Crop");
			return;
		}
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();

		if (!isMagickedHoe(item)) {
			PlayerMessager.debugLog("[HoeTool] Not magicked tool");
			return;
		}

		// Apply use cost
		ItemMeta itemMeta = item.getItemMeta();
		int resultantDamge = ((Damageable) itemMeta).getDamage() + HoeToolRecipe.toolUseCostMap.get(item.getType());
		if (resultantDamge > item.getType().getMaxDurability()) {
			item.setAmount(0);
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
			player.playEffect(EntityEffect.ENTITY_POOF);
			PlayerMessager.debugLog("Break");
		} else {
			((Damageable) itemMeta).setDamage(resultantDamge);
			item.setItemMeta(itemMeta);
		}

		// Replant
		new RegrowthRunnable(block.getType(), block.getLocation(), item.getType() == Material.DIAMOND_HOE)
				.runTaskLater(LaithornTools.tools, 20);

		// Alter drops
		event.setDropItems(false);
		for (ItemStack drop : alterDrops(block.getDrops(item), block, item.getType())) {
			if (drop.getAmount() > 1) {
				block.getWorld().dropItemNaturally(block.getLocation(), drop);
			}
		}

		// EXP gain
		Bukkit.getPluginManager().callEvent(new PlayerExperienceGainEvent(player,
				HoeToolRecipe.toolUseExpMap.get(item.getType()), 0, GainType.ATTUNEMENT, false));

	}

	private boolean isMagickedHoe(ItemStack item) {
		switch (item.getType()) {
		case WOODEN_HOE:
		case STONE_HOE:
		case IRON_HOE:
		case GOLDEN_HOE:
		case DIAMOND_HOE:
			NBTItem nbti = new NBTItem(item);
			NBTCompound laithorn = nbti.getCompound(Fragment.getTopLevelTag());
			if (laithorn != null)
				return laithorn.getString("module").equals("LaithornTools");
			else
				return false;
		default:
			return false;
		}
	}
	
	private int seedConsumption(Material toolType) {
		switch (toolType) {
		case WOODEN_HOE:
		case GOLDEN_HOE:
		case DIAMOND_HOE:
			return 1;
		case STONE_HOE:
			return 0;
		case IRON_HOE:
			return -3;
		default:
			return 1;
		}
	}
	
	private int bonusMaterial(Material toolType) {
		switch (toolType) {
		case WOODEN_HOE:
		case DIAMOND_HOE:
		case STONE_HOE:
			return 2;
		case IRON_HOE:
			return 0;
		case GOLDEN_HOE:
			return 5;
		default:
			return 0;
		}
	}
	
	private boolean isReadyCrop(Block block) {
		switch (block.getType()) {
		case WHEAT:
		case POTATOES:
		case CARROTS:
			return ((Ageable)block.getBlockData()).getAge() == 7;
		case BEETROOTS:
		case NETHER_WART:
		case SWEET_BERRY_BUSH:
			return ((Ageable)block.getBlockData()).getAge() == 3;
		default:
			return false;
		}
	}

	private Collection<ItemStack> alterDrops(Collection<ItemStack> drops, Block block, Material toolType) {
		Material seedMaterial = getSeedMaterial(block.getType());
		for (ItemStack item : drops) {
			int seedAmount = item.getAmount();
			if (item.getType() == seedMaterial) {
				item.setAmount(Math.max(0, seedAmount - seedConsumption(toolType)));
			} else {
				item.setAmount(seedAmount + bonusMaterial(toolType));
			}
		}
		return drops;
	}

	private Material getSeedMaterial(Material fruit) {
		switch (fruit) {
		case WHEAT:
			return Material.WHEAT_SEEDS;
		case POTATOES:
			return Material.POTATO;
		case BEETROOTS:
			return Material.BEETROOT_SEEDS;
		case CARROTS:
			return Material.CARROT;
		case NETHER_WART:
			return Material.NETHER_WART;
		case SWEET_BERRY_BUSH:
			return Material.SWEET_BERRIES;
		default:
			return null;
		}
	}

	private class RegrowthRunnable extends BukkitRunnable {

		private Material material;
		private boolean boosted;
		private Location location;

		public RegrowthRunnable(Material material, Location location, boolean boosted) {
			this.material = material;
			this.boosted = boosted;
			this.location = location;
		}

		@Override
		public void run() {
			World world = location.getWorld();

			world.spawnParticle(Particle.VILLAGER_HAPPY, location.clone().add(0.5, 0.5, 0.5), 8, 0.5, 0.5, 0.5);
			Block replantedBlock = world.getBlockAt(location);

			replantedBlock.setType(material);
			Ageable ageable = (Ageable) replantedBlock.getBlockData();
			ageable.setAge(getGrowthStage(material, boosted));
			replantedBlock.setBlockData(ageable);
		}

		private int getGrowthStage(Material material, boolean boosted) {
			switch (material) {
			case WHEAT:
			case CARROTS:
			case POTATOES:
				return boosted ? 4 : 0;
			case NETHER_WART:
			case BEETROOTS:
			case SWEET_BERRY_BUSH:
				return boosted ? 2 : 0;
			default:
				return 0;
			}
		}
	}
}
