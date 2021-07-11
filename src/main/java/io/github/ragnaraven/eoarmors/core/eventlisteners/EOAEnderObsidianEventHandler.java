package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmors.client.render.particles.ParticleEffects;
import io.github.ragnaraven.eoarmors.core.util.RangedInt;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by Ragnaraven on 9/22/2017 at 12:37 PM.
 *
 * This class manages the spawning of EnderObsidian.
 */
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EOAEnderObsidianEventHandler
{
	//public static final RangedInt CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD = new RangedInt(0, 1000,263, RangedInt.EMODES.ALWAYS);
	//public static final RangedInt CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT = new RangedInt(0, 1000,678, RangedInt.EMODES.ALWAYS);
	
	public static final RangedInt CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD = new RangedInt(0, 1000,678, RangedInt.EMODES.ALWAYS);
	public static final RangedInt CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT = new RangedInt(0, 1000,850, RangedInt.EMODES.ALWAYS);

	/**Handles when a water bucket is placed down over lava.**/
	@SubscribeEvent
	public static void enderObsidianSpawnWaterEventCheck(BlockEvent.NeighborNotifyEvent event)
	{
		Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
		World world = (World) event.getWorld();
		BlockPos pos = event.getPos();
		
		//Only in the end should any of this happen
		//if(world.dimension().location().equals(DimensionType.END_LOCATION.location()))
		if(world.dimension().location().equals(Dimension.END.location()))
		{
			//Checking for the chance here because it will save some processing I.E. it wont check surrounding BLOCKS.
			if(shouldSpawnEnderObsidian())
			{
				//Only checking for lava and water events.
				if (block == Blocks.WATER)
					trySpawnEnderObsidianWaterEvent(world, pos);
			}
		}
	}
	
	public static boolean shouldSpawnEnderObsidian ()
	{
		if(/*(EnderObsidianArmorsMod.BUILDCRAFT || EnderObsidianArmorsMod.IC2) && */CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.get() != 0)
			return EnderObsidianArmorsMod.RANDOM.nextInt(CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.max) < CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.get();
		
		return CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.get() != 0 && EnderObsidianArmorsMod.RANDOM.nextInt(CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.max) < CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.get();
	}
	
	public static void trySpawnEnderObsidianWaterEvent(World world, BlockPos pos)
	{
		if(isValidLavaSourceBlock(world, pos.below()) //Down
			|| isValidLavaSourceBlock(world, pos.north()) //North
			|| isValidLavaSourceBlock(world, pos.south()) //South
			|| isValidLavaSourceBlock(world, pos.east()) //East
			|| isValidLavaSourceBlock(world, pos.west())) //West
		{
			spawnEnderObsidian(world, pos.below());
		}
	}

	public static boolean isValidLavaSourceBlock (World world, BlockPos pos)
	{
		//If it is a lava source block.
		//Strangely, source BLOCKS are turned into flowing BLOCKS when there is a physics update. Therefore the LEVEL = 0 check
		//determines if the flowing block is equivalent to a source block. I imagine this means there will be some EO spawns
		//that should not have happened, but I am okay with that.
		return world.getBlockState(pos).getBlock() == Blocks.LAVA //Is lava
				|| (world.getBlockState(pos).getBlock() == Blocks.LAVA);// && world.getBlockState(pos).getValue(BlockWrapper.LiquidContainerBlockWrapper.LEVEL) == 0); //Is lava flowing at level 0
	}

	public static void spawnEnderObsidian(World world, BlockPos pos)
	{
		world.setBlockAndUpdate(pos, EOABlocks.ENDER_OBSIDIAN.get().defaultBlockState());
		
		if(!world.isClientSide())
			for(int i = 0; i < 5; i++)
				ParticleEffects.spawnEnderObsidianSpawnParticles(world, pos.getX(), pos.getY(), pos.getZ());
	}
}
