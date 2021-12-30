package io.github.ragnaraven.eoarmors.client.render.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;
import java.awt.*;

/**
 * Created by Ragnaraven on 7/17/2017 at 5:31 PM.
 */
public class EOAParticles {

	public static class EnderObsidianParticle extends PortalParticle {
		public EnderObsidianParticle(ClientLevel parWorld,
									  double parX, double parY, double parZ,
									  double parMotionX, double parMotionY, double parMotionZ) {
			super(parWorld, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);

			float[] color = ParticleEffects.getRandomColor();
			setColor(color[0], color[1], color[2]);
		}
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		@Nullable
		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new EnderObsidianParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
		}
	}

	public static class EnderObsidianParticleData implements ParticleOptions {
		@Override
		public ParticleType<?> getType() {
			return ParticleTypes.PORTAL;
		}

		@Override
		public void writeToNetwork(FriendlyByteBuf p_123732_) {

		}

		@Override
		public String writeToString() {
			return null;
		}
	}
}