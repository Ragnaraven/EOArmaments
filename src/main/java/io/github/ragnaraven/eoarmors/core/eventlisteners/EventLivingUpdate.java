package io.github.ragnaraven.eoarmors.core.eventlisteners;

import java.util.Random;

import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.essentials.Rarity;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
		if (event.getEntityLiving() instanceof Player)
		{
			Player player = (Player) event.getEntityLiving();
			
			if (player != null)
			{
				NonNullList<ItemStack> main = player.getInventory().items;

				//Added ! //
				//if (!player.getCommandSenderWorld().isClientSide())
				{
					for (ItemStack stack : player.getInventory().armor)
					{
						if (stack != null && EAUtils.canEnhanceArmor(stack.getItem()))
						{
							CompoundTag nbt = stack.getTag();
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
								CompoundTag nbt = NBTHelper.loadStackNBT(stack);

								if (nbt != null) {
									if (!Experience.isEnabled(nbt)) {
										boolean okay = true;

										for (int j = 0; j < ConfigHolder.SERVER.itemBlacklist.get().size(); j++) {
											if (ConfigHolder.SERVER.itemBlacklist.get().get(j).equals(stack.getItem().getRegistryName().getPath()))
												okay = false;
										}

										if (ConfigHolder.SERVER.itemWhitelist.get().size() != 0) {
											okay = false;
											for (int k = 0; k < ConfigHolder.SERVER.itemWhitelist.get().size(); k++)
												if (ConfigHolder.SERVER.itemWhitelist.get().get(k).equals(stack.getItem().getRegistryName().getPath()))
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