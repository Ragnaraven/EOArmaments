package io.github.ragnaraven.eoarmors.client.render.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * Created by Ragnaraven on 7/17/2017 at 5:31 PM.
 */
public class EnderObsidianBlockParticles extends SpriteTexturedParticle {

	private final IAnimatedSprite sprites;

	public EnderObsidianBlockParticles(ClientWorld world, double x, double y, double z, EnderObsidianParticleData eopd, IAnimatedSprite sprite) {
		super(world, x, y, z, 0, 0, 0);
		this.sprites = sprite;
		this.xd *= (double) 0.1F;
		this.yd *= (double) 0.1F;
		this.zd *= (double) 0.1F;
		float f = (float) Math.random() * 0.4F + 0.6F;
		float[] color = ParticleEffects.getRandomColor();
		this.rCol = color[0];
		this.gCol = color[1];
		this.bCol = color[2];
		this.quadSize *= 0.75F * 1f;//rpd.getScale();
		int i = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		this.lifetime = (int) Math.max((float) i * 1f, 1.0F);//rpd.getScale(), 1.0F);
		this.setSpriteFromAge(sprite);
	}

	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public float getQuadSize(float p_217561_1_) {
		return this.quadSize * MathHelper.clamp(((float) this.age + p_217561_1_) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
	}

	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age++ >= this.lifetime) {
			this.remove();
		} else {
			this.setSpriteFromAge(this.sprites);
			this.move(this.xd, this.yd, this.zd);
			if (this.y == this.yo) {
				this.xd *= 1.1D;
				this.zd *= 1.1D;
			}

			this.xd *= (double) 0.96F;
			this.yd *= (double) 0.96F;
			this.zd *= (double) 0.96F;
			if (this.onGround) {
				this.xd *= (double) 0.7F;
				this.zd *= (double) 0.7F;
			}

		}
	}

	public static class EnderObsidianParticleData implements IParticleData {

		@Override
		public ParticleType<?> getType() {
			return ParticleTypes.DUST;
		}

		@Override
		public void writeToNetwork(PacketBuffer p_197553_1_) {

		}

		@Override
		public String writeToString() {
			return null;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<EnderObsidianParticleData> {
		private final IAnimatedSprite sprites;

		public Factory(IAnimatedSprite p_i50477_1_) {
			this.sprites = p_i50477_1_;
		}

		public Particle createParticle(EnderObsidianParticleData eopd, ClientWorld world, double x, double y, double z, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			return new EnderObsidianBlockParticles(world, x, y, z, eopd, this.sprites);
		}
	}
}