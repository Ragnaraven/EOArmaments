package io.github.ragnaraven.eoarmors.common.items.tools;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.client.render.particles.ParticleEffects;
import io.github.ragnaraven.eoarmors.core.util.EOAHelpers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

/**
 * Created by Ragnaraven on 7/17/2017 at 4:00 PM.
 */
public class EnderObsidianSword extends SwordItem
{
	public static final String PLAYER_TP_DATA_TAG = "NEC_PLAYER_TP";
	public static final int TP_COOLDOWN = 10;
	public static final double TP_RANGE = 125f;
	public static final int TP_HELPER_DISTANCE_AROUND = 3;
	public static final int TP_DISTANCE_DAMAGE_DIVIDER = 12;
	
	public EnderObsidianSword(IItemTier itemTier, int damage, float speed, Item.Properties properties)
	{
		super(itemTier, damage, speed, properties);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand)
	{
		ItemStack itemStack = ((hand == Hand.MAIN_HAND) ? playerEntity.getMainHandItem() : playerEntity.getOffhandItem());

		if (EOAHelpers.CHECK_ARMOR(playerEntity) == 1)
			//ERROR CHECK SP
			playerTPCheck(itemStack, world, playerEntity);

		return super.use(world, playerEntity, hand);
	}
	
	//Should only be called if player is wearing EnderObsidianArmor and Sword
	public void playerTPCheck (ItemStack itemStack, World world, PlayerEntity player)
	{
		//if (player.worldObj.isRemote)
		{
			if(!player.getCooldowns().isOnCooldown(this))
			{
				try
				{
					RayTraceResult position = rayTrace(player, TP_RANGE);
					//if (position.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)
					{
						BlockPos pos = new BlockPos(position.getLocation());

						if(World.isInSpawnableBounds(pos))
						{
							int x = pos.getX();
							int y = pos.getY();
							int z = pos.getZ();

							int[] telePosition;
							if ((telePosition = getNearestDoubleSpaceBlockWithinTwoBlocks(player.getCommandSenderWorld(), x, y, z, player.yRot)) != null)
							{
								int distance = (int) distanceBetweenPoints(new int[]{ ((int) player.getX()), ((int)player.getY()), ((int)player.getZ())}, telePosition);

								player.getCooldowns().addCooldown(this, TP_COOLDOWN);

								//Teleport
								player.moveTo(telePosition[0], telePosition[1] + 1, telePosition[2]);

								//Calc damage
								int damage = distance / TP_DISTANCE_DAMAGE_DIVIDER;
								damage = damage == 0 ? 1 : damage;

								//Harm armor
								//Free TP in the end.
								if(!world.dimension().location().equals(Dimension.END.location()))
								{
									player.inventory.hurtArmor(DamageSource.GENERIC, damage);

									//Harm sword
									if(!world.isClientSide())
										itemStack.hurt(damage, EnderObsidianArmorsMod.RANDOM, (ServerPlayerEntity) player);
								}

								finalizeTeleport(player, telePosition[0], telePosition[1], telePosition[2]);
							}
						}
					}
				}
				catch (NullPointerException ignored) {  }
			}
		}
	}
	
	public double distanceBetweenPoints (int[] pos1, int[] pos2)
	{
		return Math.sqrt(
				Math.pow(pos2[0] - pos1[0], 2) +
						Math.pow(pos2[1] - pos1[1], 2) +
						Math.pow(pos2[2] - pos1[2], 2));
	}

	/*public RayTraceResult rayTrace(EntityLivingBase entity, double distance, float par3)
	{
		Vec3d vec3d = entity.getPositionEyes(par3);
		Vec3d vec3d1 = entity.getLook(par3);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		return entity.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
	}*/
	
	public RayTraceResult rayTrace(PlayerEntity entity, double distance)
	{
		 Vector3d vector3d = entity.getEyePosition(0);
		 Vector3d vector3d1 = entity.getViewVector(0);
		 Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
		return entity.getCommandSenderWorld().clip(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity));
	}

	public void finalizeTeleport (PlayerEntity entity, int x, int y, int z)
	{
		if(!entity.getCommandSenderWorld().isClientSide())
		{
			//Copied from Enderman class
			int i, j;
			for (i = 0; i < 128; ++i)
			{
				double d6 = i / (10 - 1.0D);
				float f = (random.nextFloat() - 0.5F) * 0.2F;
				float f1 = (random.nextFloat() - 0.5F) * 0.2F;
				float f2 = (random.nextFloat() - 0.5F) * 0.2F;
				double d7 = x + (entity.getX() - x) * d6 + (random.nextDouble() - 0.5D) * (double) entity.getBbWidth() * 2.0D;
				double d8 = y + (entity.getY() - y) * d6 + random.nextDouble() * (double) entity.getBbHeight();
				double d9 = z + (entity.getZ() - z) * d6 + (random.nextDouble() - 0.5D) * (double) entity.getBbHeight() * 2.0D;
				
				for(j = 0; j < 10; j++)
					ParticleEffects.spawnEnderObsidianParticles(entity, d7, d8, d9, f, f1, f2);
				//entity.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
			}
		}
		
		//TODO: ARE THESE BOTH NECESSARY???

		//Start
		entity.getCommandSenderWorld().playSound(entity, entity.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
		//End
		entity.getCommandSenderWorld().playSound(entity, new BlockPos(x, y, z), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}
	
	public int[] getNearestDoubleSpaceBlockWithinTwoBlocks (World world, int targetX, int targetY, int targetZ, float yaw)
	{
		//Check above player hit point to see if they aimed at a good spot first.
		int minY = //targetY > TP_HELPER_DISTANCE_AROUND ? TP_HELPER_DISTANCE_AROUND :
			1;
		int maxY = TP_HELPER_DISTANCE_AROUND;
		
		for(int y = targetY + maxY; y > targetY - minY; y--)
			if(checkValidTPPos(world, targetX, y, targetZ))
				return new int[]{targetX, y + 1, targetZ};

		int minX, maxX;
		int minZ, maxZ;
		
		if(yaw <= 45 || (yaw <= 360 && yaw >= 315))
		{//east
			minX = TP_HELPER_DISTANCE_AROUND;
			maxX = 0;
			minZ = TP_HELPER_DISTANCE_AROUND;
			maxZ = TP_HELPER_DISTANCE_AROUND;
		}
		else if(yaw > 45 && yaw < 135)
		{//north
			minZ = 0;
			maxZ = TP_HELPER_DISTANCE_AROUND;
			minX = TP_HELPER_DISTANCE_AROUND;
			maxX = TP_HELPER_DISTANCE_AROUND;
		}
		else if(yaw >= 135 && yaw <= 225)
		{//west
			minX = 0;
			maxX = TP_HELPER_DISTANCE_AROUND;
			minZ = TP_HELPER_DISTANCE_AROUND;
			maxZ = TP_HELPER_DISTANCE_AROUND;
		}
		else //Yaw >= 225 should be 315 max
		{//south
			minZ = TP_HELPER_DISTANCE_AROUND;
			maxZ = 0;
			minX = TP_HELPER_DISTANCE_AROUND;
			maxX = TP_HELPER_DISTANCE_AROUND;
		}
		
		for(int x = targetX - minX; x < targetX + maxX; x++)
			for(int z = targetZ - minZ; z < targetZ + maxZ; z++)
				for(int y = targetY - minY; y < targetY + maxY; y++)
					if(checkValidTPPos(world, x, y, z))
						return new int[] {x, y, z};

		return null;
	}
	
	/**Returns true if footY is a GROUNDED block.**/
	public boolean checkValidTPPos (World world, int footX, int footY, int footZ)
	{/*
		boolean a = !isValidTPBlock(world.getBlock(footX, footY, footZ));                    //Solid at feet
		boolean b = isValidTPBlock(world.getBlock(footX, footY + 1, footZ)); //Air or passable
		boolean c = isValidTPBlock(world.getBlock(footX, footY + 2, footZ));//Air or passable
		
		System.out.println(
				"\n" + a + " : " + world.getBlock(footX, footY, footZ).getUnlocalizedName() +
				"\n" + b + " : " + world.getBlock(footX, footY + 1, footZ).getUnlocalizedName() +
				"\n" + c + " : " + world.getBlock(footX, footY + 2, footZ).getUnlocalizedName() +
				"\n" + (a && b && c));*/
		
		//If passible find footing
		return !isValidTPBlock(world.getBlockState((new BlockPos(footX, footY, footZ))).getBlock())           //Solid at feet
				&& isValidTPBlock(world.getBlockState((new BlockPos(footX, footY + 1, footZ))).getBlock()) //Air or passable
				&& isValidTPBlock(world.getBlockState((new BlockPos(footX, footY + 2, footZ))).getBlock());//Air or passable
	}
	
	public boolean isValidTPBlock(Block block)
	{
		return block == Blocks.AIR
				|| block == Blocks.WATER
				|| block == Blocks.LAVA
				//|| block == Blocks.TALLGRASS
				|| block == Blocks.WHEAT
				//|| block == Blocks.CARPET
				//|| block == Blocks.DEADBUSH
				|| block == Blocks.CARROTS
				|| block == Blocks.WHEAT
				|| block == Blocks.RED_MUSHROOM
				|| block == Blocks.BROWN_MUSHROOM
				/*|| block == Blocks.DOUBLE_PLANT
				|| block == Blocks.FLOWING_LAVA
				|| block == Blocks.FLOWING_WATER
				|| block == Blocks.GOLDEN_RAIL*/
				|| block == Blocks.ACTIVATOR_RAIL
				|| block == Blocks.DETECTOR_RAIL
				|| block == Blocks.RAIL
				|| block == Blocks.LADDER
				|| block == Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE
				|| block == Blocks.MELON_STEM
				|| block == Blocks.NETHER_WART
				/*|| block == Blocks.POWERED_COMPARATOR
				|| block == Blocks.POWERED_REPEATER
				|| block == Blocks.UNPOWERED_COMPARATOR
				|| block == Blocks.UNPOWERED_REPEATER*/
				|| block == Blocks.PUMPKIN_STEM
				//|| block == Blocks.REEDS
				|| block == Blocks.POTATOES
				/*|| block == Blocks.RED_FLOWER
				|| block == Blocks.TALLGRASS
				|| block == Blocks.TRAPDOOR*/
				|| block == Blocks.TRIPWIRE
				|| block == Blocks.TRIPWIRE_HOOK
				|| block == Blocks.REDSTONE_TORCH
				|| block == Blocks.REDSTONE_WIRE
				/*|| block == Blocks.PORTAL
				|| block == Blocks.SAPLING
				|| block == Blocks.SNOW_LAYER*/
				|| block == Blocks.STONE_BUTTON
				|| block == Blocks.STONE_PRESSURE_PLATE
				//|| block == Blocks.UNLIT_REDSTONE_TORCH
				|| block == Blocks.TORCH
				|| block == Blocks.VINE
				;//|| block == Blocks.WALL_SIGN;
	}
}
