package io.github.ragnaraven.eoarmors.client.render.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Ragnaraven on 7/17/2017 at 5:30 PM.
 */
public class ParticleEffects
{
	private static Random random = new Random();
	
	public static void spawnEnderObsidianBlockParticles(World world, double x, double y, double z)
	{
		switch (random.nextInt(6))
		{
			case 0: //south
				x += random.nextDouble();
				y += random.nextDouble();
				break;
			case 1: //east
				z += random.nextDouble();
				y += random.nextDouble();
				break;
			case 2: //bottom
				x += random.nextDouble();
				z += random.nextDouble();
				break;
			case 3:
				y += 1; //top
				x += random.nextDouble();
				z += random.nextDouble();
				break;
			case 4: //west
				x += 1;
				z += random.nextDouble();
				y += random.nextDouble();
				break;
			case 5: //north
				z += 1;
				x += random.nextDouble();
				y += random.nextDouble();
				break;
		}

	}
	
	public static void spawnEnderObsidianSpawnParticles(World world, double x, double y, double z)
	{
		//Center on top of block;
		y += 5;
		x += 1 - random.nextDouble(); //Random diff
		z += 1 - random.nextDouble(); //Random diff

		world.addParticle(new EnderObsidianBlockParticles.EnderObsidianParticleData(), x, y, z, 0, 0, 0);
	}
	
	public static void spawnEnderObsidianParticles(Entity entity, double x, double y, double z, float f, float f1, float f2)
	{
		entity.getCommandSenderWorld().addParticle(new EnderObsidianBlockParticles.EnderObsidianParticleData(), x, y, z, 0, 0, 0);
	}
	
	public static void spawnEnderObsidianParticles(Entity entity, int quantity)
	{
		for (int i = 0; i < quantity; i++)
		{
			entity.getCommandSenderWorld().addParticle(
					new EnderObsidianBlockParticles.EnderObsidianParticleData(),
					entity.getX() + (random.nextDouble() - 0.5D) * (double) entity.getBbWidth(),
					entity.getY() + random.nextDouble() * (double) entity.getBbHeight() - 1.25,
					entity.getZ() + (random.nextDouble() - 0.5D) * (double) entity.getBbWidth(),
					(random.nextDouble() - 0.5D) * 2.0D,
					-random.nextDouble(),
					(random.nextDouble() - 0.5D) * 2.0D);
		}
	}
	
	//Some colors repeat to be more prevalent.
	public static float[][] colors = {
			new float[] {53f/255, 115f/255, 125f/255},
			new float[] {53f/255, 115f/255, 125f/255},
			new float[] {53f/255, 115f/255, 125f/255},
			new float[] {10f/255,  52f/255,  46f/255},
			new float[] {17f/255,  48f/255,  50f/255},
			new float[] {16f/255,  88f/255,  76f/255},
			new float[] {16f/255,  88f/255,  76f/255},
			new float[] {16f/255,  88f/255,  76f/255}
	};
	
	public static float[] getRandomColor ()
	{
		return colors[random.nextInt(colors.length)];
	}
}