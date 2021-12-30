package io.github.ragnaraven.eoarmors.common.blocks;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Created by Ragnaraven on 7/15/2017 at 7:09 PM.
 */
public class EnderObsidian extends BlockBase
{
	public EnderObsidian(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(Level world, int x, int y, int z, Random random)
	{
		/*if (random.nextInt(3) == 0)
			ParticleEffects.spawnEnderObsidianBlockParticles(world, x, y, z);*/
	}
}
