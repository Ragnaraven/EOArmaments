package io.github.ragnaraven.eoarmaments.core.eventlisteners;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.client.render.particles.ParticleEffects;
import io.github.ragnaraven.eoarmaments.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmaments.common.items.EOAItems;
import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.List;

import static io.github.ragnaraven.eoarmaments.core.util.EOAHelpers.*;

/**
 * Created by Ragnaraven on 7/15/2017 at 2:48 AM.
 *
 * This class handles all tool healing events, as well as ender obsidian creation
 */
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EOABlockBreakEventHandler
{
	//On mine lava
	//public static final RangedInt LAVA_SPAWN_CHANCE = new RangedInt(11, RangedInt.EMODES.ALWAYS);
	
	//On mine exp
	//public static final RangedInt EXP_CHANCE = new RangedInt(5, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt BASE_EXP = new RangedInt(2, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt EXP_RANDOM = new RangedInt(3, RangedInt.EMODES.ALWAYS);
	
	//On mine tool heal
	//public static final RangedInt OBSIDIAN_TO_MINE_TO_FULL_HEALTH = new RangedInt(25, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt ADDITIONAL_OBSIDIAN_TO_MINE_NON_PICK_TO_FULL_HEALTH = new RangedInt(18, RangedInt.EMODES.ALWAYS); //Added to above

	//On mine armor heal
	//public static final RangedInt HEAL_ARMOR_CHANCE = new RangedInt(0, 1000, 700, RangedInt.EMODES.ALWAYS); //Out 0f 1000
	//public static final RangedInt HEAL_ARMOR_DURABILITY = new RangedInt(1, Integer.MAX_VALUE, 2, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt ARMOR_HEAL_EXP_DROP = new RangedInt(1, RangedInt.EMODES.ALWAYS);
	
	//On harvest
	//public static final RangedInt CHANCE_TO_SILK_TOUCH_OBSIDIAN = new RangedInt(7, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt CHANCE_CONVERT_OBSIDIAN_TO_ENDER = new RangedInt(10, RangedInt.EMODES.ALWAYS);
	
	public static boolean ENABLE_FORTUNE = true;
	//Redo this to be smarter, maybe add config, if requested.
	private static final int CHANCE_BUILT_IN_FORTUNE_EFFECT = 350; //out 1000
	private static final float FORTUNE_EFFECT_MULTIPLIER_BASE = 2f;
	private static final float FORTUNE_EFFECT_MULTIPLIER_ADDITIONS = 0.5f;
	private static final float FORTUNE_EFFECT_MULTIPLIER_ADDITIONS_RANDOM = 0.5f;
	private static final int FORTUNE_EFFECT_EXTRA_PICK_HURT = 5;
	private static final int FORTUNE_EFFECT_DROP_0 = 16;
	private static final int FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM = 3; //Higher number = less chance to get the max
	private static final int FORTUNE_EFFECT_MAX_ADDITION_CHANCE = 64;

	private static final int FORTUNE_EFFECT_QUARTZ_MULTIPLIER = 3;
	private static final int FORTUNE_EFFECT_NETHER_GOLD_MULTIPLIER = 10;


	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e)
	{
		if (e.getPlayer() != null)
		{
			Player player = e.getPlayer();
			
			int armor = CHECK_ARMOR(player);
			
			if(armor == -1)
				return; //-1 means no match for set.

			//If here, player is wearing all obsidian or enderObsidian armor. Can be checked by armor val
			try
			{
				//Either set
				if (CHECK_PICK_AGAINST_SET(armor, player))
				{
					if (e.getState().getBlock() == Blocks.OBSIDIAN)
					{
						DO_TOOL_HEAL(player);
						DO_ARMOR_HEAL(e, player);
						doLavaDrop(e);
						doExp(e);
					}
				}
			}
			catch (NullPointerException ignored) { }
		}
	}

	private static void doLavaDrop (final BlockEvent.BreakEvent e)
	{
		Level world = (Level) e.getWorld();

		if(ConfigHolder.SERVER.LAVA_SPAWN_CHANCE.get() != 0)
		{
			if (world.isClientSide())
			{
				if(!world.dimension().equals(Level.END))
				{
					if (EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.LAVA_SPAWN_CHANCE.get()) == 0)
					{
						//Cancel the drop, set the block.
						world.setBlockAndUpdate(e.getPos(), Blocks.LAVA.defaultBlockState());
						e.setCanceled(true);
					}
				}
			}
		}
	}

	private static void doExp (BlockEvent.BreakEvent e)
	{
		if (ConfigHolder.SERVER.EXP_CHANCE.get() != 0)
		{
			if (EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.EXP_CHANCE.get()) == 0)
			{
				e.setExpToDrop(e.getExpToDrop() + ConfigHolder.SERVER.BASE_EXP.get());

				if(ConfigHolder.SERVER.EXP_RANDOM.get() != 0)
					e.setExpToDrop(e.getExpToDrop() + EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.EXP_RANDOM.get()));
			}
		}
	}

	/**Catch for nullPointer if calling this**/
	private static void DO_TOOL_HEAL(Player player)
	{
		try
		{
			if(ConfigHolder.SERVER.OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE.get() != 0)
			{
				int pickHeal = player.getMainHandItem().getMaxDamage() / ConfigHolder.SERVER.OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE.get();
				pickHeal = pickHeal <= 0 ? 1 : pickHeal; //I have no clue why I am setting it to 1 but it heals the pick fully so..
				player.getMainHandItem().setDamageValue(player.getMainHandItem().getDamageValue() - pickHeal);
			}

			//Doesnt matter if pick is 0 here.
			if(ConfigHolder.SERVER.OBSIDIAN_TO_MINE_FULL_HEALTH_INVENTORY.get() != 0)
			{
				for (int i = 0; i < player.getInventory().getContainerSize(); i++)
					if (player.getInventory().getItem(i) != ItemStack.EMPTY && player.getInventory().getItem(i) != player.getMainHandItem()) //Current item already healed
					{
						//Only heals TOOLS. Not armor in inv. Must be wearing to heal.
						ItemStack stack = player.getInventory().getItem(i);
						Item item = stack.getItem();
						if (       item == EOAItems.OBSIDIAN_AXE.get()
								|| item == EOAItems.ENDER_OBSIDIAN_AXE.get()

								|| item == EOAItems.OBSIDIAN_PICKAXE.get()
								|| item == EOAItems.ENDER_OBSIDIAN_PICKAXE.get()

								|| item == EOAItems.OBSIDIAN_SHOVEL.get()
								|| item == EOAItems.ENDER_OBSIDIAN_SHOVEL.get()

								|| item == EOAItems.OBSIDIAN_HOE.get()
								|| item == EOAItems.ENDER_OBSIDIAN_HOE.get()

								|| item == EOAItems.OBSIDIAN_SWORD.get()
								|| item == EOAItems.ENDER_OBSIDIAN_SWORD.get())
						{
							int otherHeal = stack.getMaxDamage() / (ConfigHolder.SERVER.OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE.get() + ConfigHolder.SERVER.OBSIDIAN_TO_MINE_FULL_HEALTH_INVENTORY.get());
							otherHeal = Math.max(otherHeal, 0);

							//System.out.println("Healing tool by: " + otherHeal + " out of " + stack.getMaxDamage());

							stack.setDamageValue(stack.getDamageValue() - otherHeal);
						}
					}
			}
		}
		catch (Exception ignored) { }
	}

	/**Catch for nullPointer if calling this**/
	private static void DO_ARMOR_HEAL(BlockEvent.BreakEvent e, Player player)
	{
		if (ConfigHolder.SERVER.HEAL_ARMOR_CHANCE.get() != 0)
		{
			if (EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.HEAL_ARMOR_CHANCE.get()) == 0)
			{
				e.setExpToDrop(e.getExpToDrop() + ConfigHolder.SERVER.HEAL_ARMOR_EXP_DROP_AMOUNT.get());

				int armorHeal;

				for (int i = 0; i < 4; i++)
				{
					armorHeal = player.getInventory().armor.get(i).getDamageValue() - ConfigHolder.SERVER.HEAL_ARMOR_DURABILITY.get();
					armorHeal = Math.max(armorHeal, 0);

					player.getInventory().armor.get(i).setDamageValue(armorHeal);
				}
			}
		}
	}

	public static List<ItemStack> ON_HARVEST_DROPS(Player player, Block block, List<ItemStack> drops)
	{
		int eblockState = -1;

		if (block == Blocks.OBSIDIAN)
			eblockState = LEVEL_OBSIDIAN;

		if (block == EOABlocks.ENDER_OBSIDIAN.get())
			eblockState = LEVEL_ENDER_OBSIDIAN;

		int pick = CHECK_PICK(player);

		if (pick == -1) //Nothing special if not Obisidian or Ender Obsidian pick
			return drops;

		//If here, player is using a pick
		try
		{
			int armor = CHECK_ARMOR(player);
			//Pick and armor are the same, suit has modifiers
			if (pick == armor)
			{
				//if mining ANYTHING except Obsidian or Ender Obsidian
				if (eblockState == -1) //Fortune effects are applied for non-obsidian BLOCKS.
				{
					if (armor == LEVEL_ENDER_OBSIDIAN) //IF wearing EO
					{
						//If here, is wearing eo armor and pick
						//Only ender obsidian set. Since check pick worked, we dont need to check again.
						//Add extra
						if (ENABLE_FORTUNE) //&& EnderObsidianArmorsMod.RANDOM.nextInt(1000) < CHANCE_BUILT_IN_FORTUNE_EFFECT)
						{//If here, apply fortune effect.

							float multiplier = FORTUNE_EFFECT_MULTIPLIER_BASE;

							int extra = EnderObsidianArmorsMod.RANDOM.nextInt(FORTUNE_EFFECT_MAX_ADDITION_CHANCE);

							//Currently a 1 in 32 chance for 5x multiplier
							for (int i = FORTUNE_EFFECT_MAX_ADDITION_CHANCE / FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM; i > 0; i /= FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM)
								if (extra < i) {
									float mult = FORTUNE_EFFECT_MULTIPLIER_ADDITIONS;
									mult += EnderObsidianArmorsMod.RANDOM.nextFloat(FORTUNE_EFFECT_MULTIPLIER_ADDITIONS_RANDOM);
									multiplier += mult;
								}

							//Apply extras
							Item selectedDropItem = GET_ORE_DROP(block, drops);

							//Sum up the total number of drops.
							int total = 0;
							for (ItemStack itemStack : drops)
								if(itemStack.getItem() == selectedDropItem)
									total += itemStack.getCount();

							if (selectedDropItem != null)
							{
								int count = (int) (total * multiplier);

								System.out.println("COUNT " + count + " MULT" + multiplier);

								//Add extra quartz.
								if (block == Blocks.NETHER_QUARTZ_ORE && selectedDropItem == Items.QUARTZ)
									count *= FORTUNE_EFFECT_QUARTZ_MULTIPLIER;

								if (block == Blocks.NETHER_GOLD_ORE && selectedDropItem == Items.GOLD_NUGGET)
									count *= FORTUNE_EFFECT_NETHER_GOLD_MULTIPLIER;

								/*int OUT_count = count;
								Block OUT_block = block;
								Item OUT_item = selectedDropItem;*/

								//Remove all stacks with the original item.
								for(int i = 0; i < drops.size(); i++)
								{
									ItemStack stack = drops.get(i);
									if(stack.getItem() == selectedDropItem)
										drops.remove(stack);
								}

								//Add stacks while needed.
								while(count > 0) {
									int stack = Math.min(64, count);
									count -= stack;
									drops.add(new ItemStack(selectedDropItem, stack));
								}

								//Damage pick proportionally
								//WONT WORK NOW THAT MULT MAY BE < 1 player.getMainHandItem().setDamageValue((int) (player.getMainHandItem().getDamageValue() + multiplier * FORTUNE_EFFECT_EXTRA_PICK_HURT));

								//Spawn particles for a magical effect.
								for (int i = 0; i < 15; i++)
									ParticleEffects.spawnEnderObsidianSpawnParticles(player.level, player.getX(), player.getY(), player.getZ());
							}
						}
						else //If here, they were unlucky, lets see if they are even less lucky
						{
							if (EnderObsidianArmorsMod.RANDOM.nextInt(FORTUNE_EFFECT_DROP_0) == 0) {
								drops.clear();
								return drops;
							}
						}
					}

					return drops;
				}

				//If mining obisidian and wearing matching set with matching pick
				if (eblockState == LEVEL_OBSIDIAN)
				{
					//DO_TOOL_HEAL(player);

					//If has silk touch
					if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player) > 0)
					{
						//If in end and mining normal obsidian with suit, chance to drop ender
						if (player.getCommandSenderWorld().dimension().equals(Level.END)
								&& EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.CHANCE_TO_CONVERT_OBSIDIAN_TO_ENDER_OBSIDIAN.get()) == 0)
						{
							//System.out.println("EO");
							var itemStack = new ItemStack(EOABlocks.ENDER_OBSIDIAN.get());
							itemStack.setCount(1);

							drops.clear();
							drops.add((itemStack));

							return drops; //If we dropped ender, stop everything else
						}
						else if (EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.CHANCE_TO_SILK_TOUCH_OBSIDIAN.get()) == 0)
						{
							return drops; //Do not clear.
						}
					}

					//If here, didnt have silk touch... rip obsidian.
				}
			}
			else
			{
				//If here, mixed picks with armor sets, can be mining any block at all.
				if (eblockState == LEVEL_OBSIDIAN)
					return drops;
			}
		}
		catch (NullPointerException ignored)
		{
			ignored.printStackTrace();
		}

		return drops;
	}

	private static Item GET_ORE_DROP (Block block, List<ItemStack> drops)
	{
		/*Block OUTBLOCK2 = block;
		Item OUTITEM2 = block.asItem();
		Item OUTITEM22 = drops.get(0).getItem();
		String x = "";*/

		//Make sure the block mined isnt what was dropped
		if(drops.contains(new ItemStack(block.asItem())))
			return null;

		Item item = drops.get(0).getItem();

		String orenames_raw = block.getRegistryName().toString();
		orenames_raw = orenames_raw.substring(orenames_raw.indexOf(":") + 1);
		String dropname = item.getRegistryName().toString();
		dropname = dropname.substring(dropname.indexOf(":") + 1);

		if(dropname.equals(orenames_raw))
			return null;

		String orenames = orenames_raw;
		if(orenames.endsWith("ore")) orenames 		  = orenames.substring(0, orenames.length() - "ore".length());
		if(orenames.startsWith("deepslate")) orenames = orenames.substring("deepslate".length());
		orenames = orenames.replaceAll("_", "");

		if(dropname.contains(orenames)
			|| dropname.contains("raw")
			|| (dropname.contains("nugget") && orenames.contains("nether"))
			|| (dropname.contains("quartz") && orenames.contains("nether"))
		)
			return item;

		return null;
		//return block.getRegistryName().toString().endsWith("_ore") && drops.get(0).getItem().getRegistryName().toString().contains("");
	}
}