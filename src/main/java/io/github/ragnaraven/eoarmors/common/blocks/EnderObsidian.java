package io.github.ragnaraven.eoarmors.common.blocks;

import io.github.ragnaraven.eoarmors.client.render.particles.ParticleEffects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Created by Ragnaraven on 7/15/2017 at 7:09 PM.
 */
public class EnderObsidian extends BlockBase
{
	public EnderObsidian(Properties properties) {
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (random.nextInt(3) == 0)
			ParticleEffects.spawnEnderObsidianBlockParticles(world, x, y, z);
	}
}
