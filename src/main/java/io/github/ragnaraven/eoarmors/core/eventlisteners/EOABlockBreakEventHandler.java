package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmors.common.items.EOAItems;
import io.github.ragnaraven.eoarmors.client.render.particles.ParticleEffects;
import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static io.github.ragnaraven.eoarmors.core.util.EOAHelpers.*;

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
	private static final int FORTUNE_EFFECT_MULTIPLIER_BASE = 2;
	private static final int FORTUNE_EFFECT_EXTRA_PICK_HURT = 5;
	private static final int FORTUNE_EFFECT_DROP_0 = 16;
	private static final int FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM = 3; //Higher number = less chance to get the max
	private static final int FORTUNE_EFFECT_MAX_ADDITION_CHANCE = 64;

	private static final int FORTUNE_EFFECT_QUARTZ_MULTIPLIER = 3;

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e)
	{
		if (e.getPlayer() != null)
		{
			PlayerEntity player = e.getPlayer();
			
			int armor = CHECK_ARMOR(player);
			
			if(armor == -1)
				return; //-1 means no match for set.

			//If here, player is wearing all of obsidian or enderObsidian armor. Can be checked by armor val
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
						//doExp(e);
					}
				}
			}
			catch (NullPointerException ignored) { }
		}
	}

	private static void doLavaDrop (final BlockEvent.BreakEvent e)
	{
		World world = (World) e.getWorld();

		if(ConfigHolder.SERVER.LAVA_SPAWN_CHANCE.get() != 0)
		{
			if (world.isClientSide())
			{
				if(!world.dimension().location().equals(Dimension.END.location()))
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
	private static void DO_TOOL_HEAL(PlayerEntity player)
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
				for (int i = 0; i < player.inventory.getContainerSize(); i++)
					if (player.inventory.getItem(i) != ItemStack.EMPTY && player.inventory.getItem(i) != player.getMainHandItem()) //Current item already healed
					{
						//Only heals TOOLS. Not armor in inv. Must be wearing to heal.
						ItemStack stack = player.inventory.getItem(i);
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
	private static void DO_ARMOR_HEAL(BlockEvent.BreakEvent e, PlayerEntity player)
	{
		if (ConfigHolder.SERVER.HEAL_ARMOR_CHANCE.get() != 0)
		{
			if (EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.HEAL_ARMOR_CHANCE.get()) == 0)
			{
				e.setExpToDrop(e.getExpToDrop() + ConfigHolder.SERVER.HEAL_ARMOR_EXP_DROP_AMOUNT.get());

				int armorHeal;

				for (int i = 0; i < 4; i++)
				{
					armorHeal = player.inventory.armor.get(i).getDamageValue() - ConfigHolder.SERVER.HEAL_ARMOR_DURABILITY.get();
					armorHeal = Math.max(armorHeal, 0);

					player.inventory.armor.get(i).setDamageValue(armorHeal);
				}
			}
		}
	}

	public static ItemStack ON_HARVEST_DROPS(PlayerEntity player, Block block, ItemStack drops)
	{
		World world = (World) player.getCommandSenderWorld();

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
						if (ENABLE_FORTUNE && EnderObsidianArmorsMod.RANDOM.nextInt(1000) < CHANCE_BUILT_IN_FORTUNE_EFFECT)
						{//If here, apply fortune effect.

							int multiplier = FORTUNE_EFFECT_MULTIPLIER_BASE;

							int extra = EnderObsidianArmorsMod.RANDOM.nextInt(FORTUNE_EFFECT_MAX_ADDITION_CHANCE);

							//Currently a 1 in 32 chance for 5x multiplier
							for (int i = FORTUNE_EFFECT_MAX_ADDITION_CHANCE / FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM; i > 0; i /= FORTUNE_EFFECT_MULTIPLIER_ADDITIONAL_RANDOM)
								if (extra < i)
									multiplier++;

							//Apply extras
							//DEPRECATED? ONLY ONE STACK PER DROP IT SEEMS.
							//for (ItemStack itemStack : drops)
							{
								if (CHECK_ORE_RELATIONSHIP(block, drops))
								{
									int count = drops.getCount() * multiplier;

									//Add extra quartz.
									if (block == Blocks.NETHER_QUARTZ_ORE && drops.getItem() == Items.QUARTZ)
										count *= FORTUNE_EFFECT_QUARTZ_MULTIPLIER;

									//Cap the max to 64.
									drops.setCount(Math.min(count, 64));

									//Damage pick proportionally
									player.getMainHandItem().setDamageValue(player.getMainHandItem().getDamageValue() - multiplier * FORTUNE_EFFECT_EXTRA_PICK_HURT);

									//Spawn particles for a magical effect.
									if (!world.isClientSide())
										for (int i = 0; i < 15; i++)
											ParticleEffects.spawnEnderObsidianSpawnParticles(world, player.getX(), player.getY(), player.getZ());
								}
							}
						}
						else //If here, they were unlucky, lets see if they are even less lucky
						{
							if (EnderObsidianArmorsMod.RANDOM.nextInt(FORTUNE_EFFECT_DROP_0) == 0)
								return ItemStack.EMPTY;
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
						if (player.getCommandSenderWorld().dimension().location().equals(Dimension.END.location())
								&& EnderObsidianArmorsMod.RANDOM.nextInt(ConfigHolder.SERVER.CHANCE_TO_CONVERT_OBSIDIAN_TO_ENDER_OBSIDIAN.get()) == 0)
						{
							//System.out.println("EO");
							drops = new ItemStack(EOABlocks.ENDER_OBSIDIAN.get());
							drops.setCount(1);

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

	private static boolean CHECK_ORE_RELATIONSHIP(Block block, ItemStack itemStack)
	{
		//WEIRD BUT SUPPORTED ORES


		//MC QUARTZ
		//GC DESH tile.mars item.raw_desh
		//GC SAPP tile.basic_block_moon item.lunar_sapphire
		//GC SILIC1 tile.basic_block_core item.basic_item.raw_silicon
		//GC SILIC2 tile.venus item.basic_item.raw_silicon
		//GC SOLARDUST tile.venus item.solar_dust
		//GC QUARTZ tile.venus item.netherquartz
		//GC CHEESE tile.basic_block_moon item.cheese_curd
		//GC ILLEMITE tile.asteroids_block item.shard_titanium


		//boolean isOre = blockName.contains("ore");
		//boolean dropsNonOre = !blockName.equals(itemName) && !itemName.contains("ore");

		//System.out.println(blockName + " " + itemName + " " + isOre + " " + dropsNonOre);

		return block == Blocks.NETHER_QUARTZ_ORE && itemStack.getItem() == Items.QUARTZ;//MC QUARTZ

		/*return (isOre && dropsNonOre)
			    || (blockName.equals("tile.netherquartz") 	  && itemName.equals("item.netherquartz")) //MC QUARTZ
				|| (blockName.equals("tile.mars") 			  && itemName.equals("item.raw_desh")) //GC DESH
				|| (blockName.equals("tile.basic_block_moon") && itemName.equals("item.lunar_sapphire")) //GC SAPPHIRE
				|| (blockName.equals("tile.basic_block_core") && itemName.equals("item.basic_item.raw_silicon")) //GC SILIC1
				|| (blockName.equals("tile.venus") 			  && itemName.equals("item.basic_item.raw_silicon")) //GC SILIC2
				|| (blockName.equals("tile.venus")            && itemName.equals("item.solar_dust")) //GC SOLARDUST
				|| (blockName.equals("tile.venus")            && itemName.equals("item.netherquartz")) //GC QUARTZ
				|| (blockName.equals("tile.basic_block_moon") && itemName.equals("item.cheese_curd")) //GC CHEESE
				|| (blockName.equals("tile.asteroids_block")  && itemName.equals("item.shard_titanium"))//GC ILLEMITE
		;*/
	}


}