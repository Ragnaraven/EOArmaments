package io.github.ragnaraven.eoarmors.core.eventlisteners;

import java.util.Random;

import io.github.ragnaraven.eoarmors.config.Config;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventLivingUpdate
{
	//this needs to be a player capability or this will be really random in Multiplayer!
	private int count = 0;
	
	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			
			if (player != null)
			{
				NonNullList<ItemStack> main = player.inventory.items;
				
				if (player.getCommandSenderWorld().isClientSide())
				{
					for (ItemStack stack : player.inventory.armor)
					{
						if (stack != null && EAUtils.canEnhanceArmor(stack.getItem()))
						{
							CompoundNBT nbt = stack.getTag();
							float heal = Ability.REMEDIAL.getLevel(nbt);
							if (Ability.REMEDIAL.hasAbility(nbt))
								if(count < 120)
								{
									count++;
								}
								else
								{
									count = 0;
									player.heal(heal);
								}
						}
					}
					for (ItemStack stack : main) {
						if (stack != ItemStack.EMPTY) {
							Item item = stack.getItem();

							if (EAUtils.canEnhance(item)) {
								CompoundNBT nbt = NBTHelper.loadStackNBT(stack);

								if (nbt != null) {
									if (!Experience.isEnabled(nbt)) {
										boolean okay = true;

										for (int j = 0; j < Config.itemBlacklist.size(); j++) {
											if (Config.itemBlacklist.get(j).equals(stack.getItem().getRegistryName().getPath()))
												okay = false;
										}

										if (Config.itemWhitelist.size() != 0) {
											okay = false;
											for (int k = 0; k < Config.itemWhitelist.size(); k++)
												if (Config.itemWhitelist.get(k).equals(stack.getItem().getRegistryName().getPath()))
													okay = true;
										}

										if (okay) {
											Experience.enable(nbt, true);
											Rarity rarity = Rarity.getRarity(nbt);
											Random rand = player.getCommandSenderWorld().getRandom();

											if (rarity == Rarity.DEFAULT) {
												rarity = Rarity.getRandomRarity(rand);
												rarity.setRarity(nbt);
												NBTHelper.saveStackNBT(stack, nbt);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}