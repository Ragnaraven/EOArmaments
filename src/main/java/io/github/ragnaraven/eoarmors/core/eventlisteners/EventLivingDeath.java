package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
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
	public void onLivingDeath(LivingDeathEvent event)
	{
		if (event.getSource().getDirectEntity() instanceof Player && !(event.getSource().getDirectEntity() instanceof FakePlayer))
		{
			Player player = (Player) event.getSource().getDirectEntity();
			
			ItemStack stack;
			if(EventLivingHurt.bowfriendlyhand == null)
				stack = player.getItemInHand(player.getUsedItemHand());
			else
				stack = player.getItemInHand(EventLivingHurt.bowfriendlyhand);
			
			if (stack != ItemStack.EMPTY && EAUtils.canEnhanceMelee(stack.getItem()))
			{
				CompoundTag nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
					if(nbt.contains("EA_ENABLED"))
					{
						if (Ability.ETHEREAL.hasAbility(nbt))
						{
							player.getMainHandItem().setDamageValue((player.getMainHandItem().getDamageValue() - (Ability.ETHEREAL.getLevel(nbt)*2)));
						}
						addBonusExperience(event, nbt);
						updateLevel(player, stack, nbt);
						NBTHelper.saveStackNBT(stack, nbt);
					}
			}
			else if (stack != ItemStack.EMPTY && EAUtils.canEnhanceRanged(stack.getItem()))
			{
				CompoundTag nbt = NBTHelper.loadStackNBT(stack);

				if (nbt != null)
					if(nbt.contains("EA_ENABLED"))
					{
						if (Ability.ETHEREAL.hasAbility(nbt))
						{
							player.getMainHandItem().setDamageValue((player.getMainHandItem().getDamageValue() - (Ability.ETHEREAL.getLevel(nbt)*2+1)));
						}
						addBonusExperience(event, nbt);
						updateLevel(player, stack, nbt);
					}
			}
		}
		else if (event.getSource().getDirectEntity() instanceof Arrow)
		{
			Arrow arrow = (Arrow) event.getSource().getDirectEntity();
			
			//TODO: ERROR CHECK
			//if (EAUtils.getEntityByUniqueId() instanceof Player && EAUtils.getEntityByUniqueId(arrow.shootingEntity) != null)
			if (arrow.getOwner() instanceof Player && arrow.getOwner() != null)
			{
				Player player = (Player) arrow.getOwner();
				if (player != null)
				{
					ItemStack stack = player.getMainHandItem();

					if (stack != ItemStack.EMPTY)
					{
						CompoundTag nbt = NBTHelper.loadStackNBT(stack);

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
	private static void addBonusExperience(LivingDeathEvent event, CompoundTag nbt)
	{
		if (Experience.getLevel(nbt) < (ConfigHolder.SERVER.maxLevel.get()))
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
	private static void updateLevel(Player player, ItemStack stack, CompoundTag nbt)
	{
		int level = Experience.getNextLevel(player, stack, nbt, Experience.getLevel(nbt), Experience.getExperience(nbt));
		Experience.setLevel(nbt, level);
	}
}