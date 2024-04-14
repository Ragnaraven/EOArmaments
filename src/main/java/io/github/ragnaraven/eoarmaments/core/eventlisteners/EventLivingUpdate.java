package io.github.ragnaraven.eoarmaments.core.eventlisteners;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import io.github.ragnaraven.eoarmaments.core.essentials.Ability;
import io.github.ragnaraven.eoarmaments.core.essentials.Experience;
import io.github.ragnaraven.eoarmaments.core.essentials.Rarity;
import io.github.ragnaraven.eoarmaments.core.util.EAUtils;
import io.github.ragnaraven.eoarmaments.core.util.NBTHelper;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MOD_ID)
public class EventLivingUpdate
{
	//this needs to be a player capability or this will be really random in Multiplayer!
	private static int count = 0;
	
	@SubscribeEvent
	public static void onUpdate(LivingEvent.LivingTickEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();
			
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
											if (ConfigHolder.SERVER.itemBlacklist.get().get(j).equals(stack.getItem().getName(stack).getString()))
												okay = false;
										}

										if (!ConfigHolder.SERVER.itemWhitelist.get().isEmpty()) {
											okay = false;
											for (int k = 0; k < ConfigHolder.SERVER.itemWhitelist.get().size(); k++)
												if (ConfigHolder.SERVER.itemWhitelist.get().get(k).equals(stack.getItem().getName(stack).getString()))
													okay = true;
										}

										if (okay) {
											Experience.enable(nbt, true);
											Rarity rarity = Rarity.getRarity(nbt);
											var rand = player.getCommandSenderWorld().getRandom();

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