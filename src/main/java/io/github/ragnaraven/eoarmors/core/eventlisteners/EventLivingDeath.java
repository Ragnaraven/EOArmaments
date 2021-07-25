package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.config.Config;
import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Updates weapon information when killing a target with a valid weapon. Used to update experience,
 * level, abilities, and so on.
 *
 */
@Mod.EventBusSubscriber
public class EventLivingDeath
{
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if (event.getSource().getDirectEntity() instanceof PlayerEntity && !(event.getSource().getDirectEntity() instanceof FakePlayer))
		{
			PlayerEntity player = (PlayerEntity) event.getSource().getDirectEntity();
			
			ItemStack stack;
			if(EventLivingHurt.bowfriendlyhand == null)
				stack = player.getItemInHand(player.getUsedItemHand());
			else
				stack = player.getItemInHand(EventLivingHurt.bowfriendlyhand);
			
			if (stack != ItemStack.EMPTY && EAUtils.canEnhanceMelee(stack.getItem()))
			{
				CompoundNBT nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
					if(nbt.contains("EA_ENABLED"))
					{
						if (Ability.ETHEREAL.hasAbility(nbt))
						{
							player.inventory.getCarried().setDamageValue((player.inventory.getCarried().getDamageValue() - (Ability.ETHEREAL.getLevel(nbt)*2)));
						}
						addBonusExperience(event, nbt);
						updateLevel(player, stack, nbt);
						NBTHelper.saveStackNBT(stack, nbt);
					}
			}
			else if (stack != ItemStack.EMPTY && EAUtils.canEnhanceRanged(stack.getItem()))
			{
				CompoundNBT nbt = NBTHelper.loadStackNBT(stack);

				if (nbt != null)
					if(nbt.contains("EA_ENABLED"))
					{
						if (Ability.ETHEREAL.hasAbility(nbt))
						{
							player.inventory.getCarried().setDamageValue((player.inventory.getCarried().getDamageValue() - (Ability.ETHEREAL.getLevel(nbt)*2+1)));
						}
						addBonusExperience(event, nbt);
						updateLevel(player, stack, nbt);
					}
			}
		}
		else if (event.getSource().getDirectEntity() instanceof ArrowEntity)
		{
			ArrowEntity arrow = (ArrowEntity) event.getSource().getDirectEntity();
			
			//TODO: ERROR CHECK
			//if (EAUtils.getEntityByUniqueId() instanceof PlayerEntity && EAUtils.getEntityByUniqueId(arrow.shootingEntity) != null)
			if (arrow.getEntity() instanceof PlayerEntity && arrow.getEntity() != null)
			{
				PlayerEntity player = (PlayerEntity) arrow.getEntity();
				if (player != null)
				{
					ItemStack stack = player.inventory.getCarried();

					if (stack != ItemStack.EMPTY)
					{
						CompoundNBT nbt = NBTHelper.loadStackNBT(stack);

						if (nbt != null)
						{
							addBonusExperience(event, nbt);
							updateLevel(player, stack, nbt);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Called everytime a target dies. Adds bonus experience based on how much health the target had.
	 * @param event
	 * @param nbt
	 */
	private static void addBonusExperience(LivingDeathEvent event, CompoundNBT nbt)
	{
		if (Experience.getLevel(nbt) < Config.maxLevel)
		{
			if (event.getEntityLiving() != null)
			{
				LivingEntity target = event.getEntityLiving();
				int bonusExperience = 0;
				
				if (target.getMaxHealth() < 10) bonusExperience = 3;
				else if (target.getMaxHealth() > 9 && target.getMaxHealth() < 20) bonusExperience = 6;
				else if (target.getMaxHealth() > 19 && target.getMaxHealth() < 50) bonusExperience = 15;
				else if (target.getMaxHealth() > 49 && target.getMaxHealth() < 100) bonusExperience = 50;
				else if (target.getMaxHealth() > 99) bonusExperience = 70;
				
				Experience.setExperience(nbt, Experience.getExperience(nbt) + bonusExperience);
			}
		}
	}
	
	/**
	 * Called everytime a target dies. Used to update the level of the weapon.
	 * @param player
	 * @param stack
	 * @param nbt
	 */
	private static void updateLevel(PlayerEntity player, ItemStack stack, CompoundNBT nbt)
	{
		int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
		Experience.setLevel(nbt, level);
	}
}