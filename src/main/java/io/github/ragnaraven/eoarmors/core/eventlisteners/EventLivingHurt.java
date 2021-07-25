package io.github.ragnaraven.eoarmors.core.eventlisteners;

import java.util.Collection;

import com.google.common.collect.Multimap;

import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.config.ServerConfig;
import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.essentials.Rarity;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventLivingHurt
{
	//this needs to be a player capability or this will be really random in Multiplayer!
	public static Hand bowfriendlyhand;
	
	@SubscribeEvent
	public void onArrowHit(ProjectileImpactEvent event)
	{
		if(event.getEntity() instanceof ArrowEntity)
		{
			//TODO: CRASH CHECK? ERROR CHECK? WHO IS shootingEntity now??????
			//if(EAUtils.getEntityByUniqueId(((ArrowEntity)event.getEntity()).shootingEntity) instanceof PlayerEntity && EAUtils.getEntityByUniqueId(((ArrowEntity)event.getEntity()).shootingEntity) != null)
			if(((ArrowEntity)event.getEntity()).getOwner() instanceof PlayerEntity && ((ArrowEntity)event.getEntity()).getOwner() != null)
			{
				PlayerEntity player=(PlayerEntity) ((ArrowEntity)event.getEntity()).getOwner();

				//TODO: ERROR CHECK. ENTITY == NULL is that the same as a miss??
				//if(event.getRayTraceResult().entity == null && player != null)
				if(event.getRayTraceResult().getType() == RayTraceResult.Type.MISS && player != null)
					bowfriendlyhand = player.getUsedItemHand();
			}
		}
	}
	
	@SubscribeEvent
	public void onArrowShoot(ArrowLooseEvent event)
	{
		bowfriendlyhand = event.getPlayer().getUsedItemHand();
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event)
	{
		if (event.getSource().getDirectEntity() instanceof PlayerEntity && !(event.getSource().getDirectEntity() instanceof FakePlayer)) //PLAYER IS ATTACKER
		{
			PlayerEntity player = (PlayerEntity) event.getSource().getDirectEntity();
			LivingEntity target = event.getEntityLiving();
			ItemStack stack;
			if(bowfriendlyhand == null)
				stack = player.getItemInHand(player.getUsedItemHand());
			else
				stack = player.getItemInHand(bowfriendlyhand);
			
			if (stack != ItemStack.EMPTY && EAUtils.canEnhanceWeapon(stack.getItem()))
			{
				CompoundNBT nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
					if(nbt.contains("EA_ENABLED"))
					{
						updateExperience(nbt, event.getAmount());
						useRarity(event, stack, nbt);
						useWeaponAbilities(event, player, target, nbt);
						updateLevel(player, stack, nbt);
					}
			}
		}
		else if (event.getEntityLiving() instanceof PlayerEntity)
		{//PLAYER IS GETTING HURT
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			Entity target = event.getSource().getDirectEntity();
			
			for (ItemStack stack : player.inventory.armor)
			{
				if (stack != null)
				{
					if (EAUtils.canEnhanceArmor(stack.getItem()))	
					{
						CompoundNBT nbt = NBTHelper.loadStackNBT(stack);
						
						if (nbt != null)
							if(nbt.contains("EA_ENABLED"))
							{
								if(EAUtils.isDamageSourceAllowed(event.getSource()))
								{
									if(event.getAmount() < (player.getMaxHealth() + player.getArmorValue()))
										updateExperience(nbt, event.getAmount());
									else
										updateExperience(nbt, 1);
									updateLevel(player, stack, nbt);
								}
								useRarity(event, stack, nbt);
								useArmorAbilities(event, player, target, nbt);
							}
					}
				}
			}
		}
	}
	
	/**
	 * Called everytime a target is hurt. Used to add experience to weapons dealing damage.
	 * @param nbt
	 */
	private void updateExperience(CompoundNBT nbt, float dealedDamage)
	{
		if (Experience.getLevel(nbt) < ConfigHolder.SERVER.maxLevel.get())
		{
			Experience.setExperience(nbt, Experience.getExperience(nbt) + 1 + (int)dealedDamage/4);
		}
	}
	
	/**
	 * Called everytime a target is hurt. Used to add dealing more damage or getting less damage.
	 * @param nbt
	 */
	private void useRarity(LivingHurtEvent event, ItemStack stack, CompoundNBT nbt)
	{
		Rarity rarity = Rarity.getRarity(nbt);
		
		if (rarity != Rarity.DEFAULT)
			if (EAUtils.canEnhanceMelee(stack.getItem()))
			{
				Multimap<Attribute, AttributeModifier> map = stack.getItem().getAttributeModifiers(EquipmentSlotType.MAINHAND, stack);
				Collection<AttributeModifier> damageCollection = map.get(Attributes.ATTACK_DAMAGE);
				AttributeModifier damageModifier = (AttributeModifier) damageCollection.toArray()[0];
				double damage = damageModifier.getAmount();
				event.setAmount((float) (event.getAmount() + damage * rarity.getEffect()));
			}
			else if(EAUtils.canEnhanceRanged(stack.getItem()))
			{
				float newdamage = (float) (event.getAmount() + (event.getAmount() * rarity.getEffect()/3));
				event.setAmount(newdamage);
			}
			else if (EAUtils.canEnhanceArmor(stack.getItem()))
				event.setAmount((float) (event.getAmount() / (1.0F + (rarity.getEffect()/5F))));
	}
	
	/**
	 * Called everytime a target is hurt. Used to use the current abilities a weapon might have.
	 * @param event
	 * @param player
	 * @param target
	 * @param nbt
	 */
	private void useWeaponAbilities(LivingHurtEvent event, PlayerEntity player, LivingEntity target, CompoundNBT nbt)
	{
		if (target != null)
		{
			// active
			if (Ability.FIRE.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.firechance.get()) == 0)
			{
				double multiplier = (Ability.FIRE.getLevel(nbt) + Ability.FIRE.getLevel(nbt)*4)/4;
				target.setRemainingFireTicks((int) (multiplier));
			}
			
			if (Ability.FROST.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.frostchance.get()) == 0)
			{
				double multiplier = (Ability.FROST.getLevel(nbt) + Ability.FROST.getLevel(nbt)*4)/3;
				target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) (20 * multiplier), 10));
			}
			
			if (Ability.POISON.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.poisonchance.get()) == 0)
			{
				double multiplier = (Ability.POISON.getLevel(nbt) + Ability.POISON.getLevel(nbt)*4)/2;
				target.addEffect(new EffectInstance(Effects.POISON, (int) (20 * multiplier), Ability.POISON.getLevel(nbt)));
			}
			
			if (Ability.INNATE.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.innatechance.get()) == 0)
			{
				double multiplier = (Ability.INNATE.getLevel(nbt) + Ability.INNATE.getLevel(nbt)*4)/3;
				target.addEffect(new EffectInstance(Effects.WITHER, (int) (20 * multiplier), Ability.INNATE.getLevel(nbt)));
			}

			if (Ability.BOMBASTIC.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.bombasticchance.get()) == 0)
			{
				double multiplierD = (Ability.BOMBASTIC.getLevel(nbt) + Ability.BOMBASTIC.getLevel(nbt)*4)/4;
				float multiplier = (float)multiplierD;
				World world = target.getCommandSenderWorld();
					
					if (!(target instanceof AnimalEntity))
					{
						world.explode(target, target.position().x, target.position().y, target.position().z, multiplier, Explosion.Mode.BREAK);
					}
			}
			
			if (Ability.CRITICAL_POINT.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.criticalpointchance.get()) == 0)
			{
				float multiplier = 0F;
				
				if (Ability.CRITICAL_POINT.getLevel(nbt) == 1) multiplier = 0.17F;
				else if (Ability.CRITICAL_POINT.getLevel(nbt) == 2) multiplier = 0.34F;
				else if (Ability.CRITICAL_POINT.getLevel(nbt) == 3) multiplier = 0.51F;

				float damage = target.getMaxHealth() * multiplier;
				event.setAmount(event.getAmount() + damage);
			}
			
			// passive
			if (Ability.ILLUMINATION.hasAbility(nbt))
			{
				target.addEffect(new EffectInstance(Effects.WEAKNESS, (20 * 5), Ability.ILLUMINATION.getLevel(nbt)));
			}
			
			if (Ability.BLOODTHIRST.hasAbility(nbt))
			{
				float addition =(float)(event.getAmount() * (Ability.BLOODTHIRST.getLevel(nbt) * 12) / 100);
				player.setHealth(player.getHealth()+addition);
			}
		}
	}
	
	private void useArmorAbilities(LivingHurtEvent event, PlayerEntity player, Entity target, CompoundNBT nbt)
	{
		if (target != null)
		{
			// active
			if (Ability.MOLTEN.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.moltenchance.get()) == 0 && target instanceof LivingEntity)
			{
				LivingEntity realTarget = (LivingEntity) target;
				double multiplier = (Ability.MOLTEN.getLevel(nbt) + Ability.MOLTEN.getLevel(nbt)*5)/4 ;
				realTarget.setRemainingFireTicks((int) (multiplier));
			}
			
			if (Ability.FROZEN.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.frozenchance.get()) == 0 && target instanceof LivingEntity)
			{
				LivingEntity realTarget = (LivingEntity) target;
				double multiplier = (Ability.FROZEN.getLevel(nbt) + Ability.FROZEN.getLevel(nbt)*5)/6 ;
				realTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, (int) (20 * multiplier), 10));
			}
			
			if (Ability.TOXIC.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.toxicchance.get()) == 0 && target instanceof LivingEntity)
			{
				LivingEntity realTarget = (LivingEntity) target;
				double multiplier = (Ability.TOXIC.getLevel(nbt) + Ability.TOXIC.getLevel(nbt)*4)/4 ;
				realTarget.addEffect(new EffectInstance(Effects.POISON, (int) (20 * multiplier), Ability.TOXIC.getLevel(nbt)));
			}
			
			if (Ability.ADRENALINE.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.adrenalinechance.get()) == 0)
			{
				double multiplier = (Ability.ADRENALINE.getLevel(nbt) + Ability.ADRENALINE.getLevel(nbt)*5)/3 ;
				player.addEffect(new EffectInstance(Effects.REGENERATION, (int) (20 * (multiplier)), Ability.ADRENALINE.getLevel(nbt)));
			}

			// passive
			if (Ability.BEASTIAL.hasAbility(nbt))
			{
				if (player.getHealth() <= (player.getMaxHealth() * 0.2F))
					player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 7, 0));
			}
			
			if (Ability.HARDENED.hasAbility(nbt) && (int) (Math.random() * ConfigHolder.SERVER.hardenedchance.get()) == 0)
			{
				event.setAmount(0F);
			}
		}
	}
	
	/**
	 * Called everytime a target is hurt. Used to check whether or not the weapon should level up.
	 * @param player
	 * @param stack
	 * @param nbt
	 */
	private void updateLevel(PlayerEntity player, ItemStack stack, CompoundNBT nbt)
	{
		int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
		Experience.setLevel(nbt, level);
		NBTHelper.saveStackNBT(stack, nbt);
	}
}