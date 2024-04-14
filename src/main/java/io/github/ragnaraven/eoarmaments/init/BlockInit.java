package io.github.ragnaraven.eoarmaments.init;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.common.blocks.EnderObsidian;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by Ragnaraven on 7/15/2017 at 7:08 PM.
 */
public class BlockInit
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnderObsidianArmorsMod.MOD_ID);

	public static final RegistryObject<Block> ENDER_OBSIDIAN = BLOCKS.register("block_ender_obsidian",
			() -> new EnderObsidian(
			BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
			.strength(3f, 15f)
			.sound(SoundType.AMETHYST)
		)
	);

}
