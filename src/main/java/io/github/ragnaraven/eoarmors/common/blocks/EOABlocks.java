package io.github.ragnaraven.eoarmors.common.blocks;

import io.github.ragnaraven.eoarmors.EOATabs;
import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.common.items.EOAItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.function.ToIntFunction;

/**
 * Created by Ragnaraven on 7/15/2017 at 7:08 PM.
 */
public class EOABlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EnderObsidianArmorsMod.MODID);

	public static final RegistryObject<Block> ENDER_OBSIDIAN = BLOCKS.register("block_ender_obsidian", () -> new EnderObsidian(
			AbstractBlock.Properties.of(Material.STONE)
			.harvestLevel(3)
			.sound(SoundType.STONE)
			/*.lightLevel(new ToIntFunction<BlockState>() {
				@Override
				public int applyAsInt(BlockState value) {
					return 3;
				}
			})*/
			.strength(50f, 6000f))
	);

}
