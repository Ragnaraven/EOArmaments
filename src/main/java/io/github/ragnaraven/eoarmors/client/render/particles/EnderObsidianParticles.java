package io.github.ragnaraven.eoarmors.client.render.particles;

import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.world.ClientWorld;

/**
 * Created by Ragnaraven on 7/17/2017 at 5:31 PM.
 */
public class EnderObsidianParticles extends PortalParticle
{
	public EnderObsidianParticles(ClientWorld parWorld,
								  double parX, double parY, double parZ,
								  double parMotionX, double parMotionY, double parMotionZ)
	{
		super(parWorld, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
		
		float[] color = ParticleEffects.getRandomColor();
		setColor(color[0], color[1], color[2]);
	}
}