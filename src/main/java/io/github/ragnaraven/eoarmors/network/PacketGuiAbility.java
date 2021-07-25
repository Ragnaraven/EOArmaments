package io.github.ragnaraven.eoarmors.network;

import java.util.function.Supplier;

import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketGuiAbility
{
	private int index;
	
	public PacketGuiAbility(int index)
	{
		this.index = index;
	}

	public static void encode(PacketGuiAbility msg, PacketBuffer buffer) {
		buffer.writeInt(msg.index);
	}

	public static PacketGuiAbility decode(PacketBuffer buf) {
		return new PacketGuiAbility(
				buf.readInt()
		);
	}

	public static void handle(PacketGuiAbility msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork
		(() ->{
					PlayerEntity player = ctx.get().getSender();

					if (player != null)
					{
						ItemStack stack = player.getMainHandItem();

						if (stack != ItemStack.EMPTY)
						{
							CompoundNBT nbt = NBTHelper.loadStackNBT(stack);

							if (EAUtils.canEnhanceWeapon(stack.getItem()))
							{
								if (Ability.WEAPON_ABILITIES.get(msg.index).hasAbility(nbt))
								{
									Ability.WEAPON_ABILITIES.get(msg.index).setLevel(nbt, Ability.WEAPON_ABILITIES.get(msg.index).getLevel(nbt) + 1);
									Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.WEAPON_ABILITIES.get(msg.index).getTier());
								}
								else
								{
									Ability.WEAPON_ABILITIES.get(msg.index).addAbility(nbt);
									if(!player.isCreative())
										player.giveExperienceLevels(-Ability.WEAPON_ABILITIES.get(msg.index).getExpLevel(nbt) + 1);
								}
							}
							else if (EAUtils.canEnhanceArmor(stack.getItem()))
							{
								if (Ability.ARMOR_ABILITIES.get(msg.index).hasAbility(nbt))
								{
									Ability.ARMOR_ABILITIES.get(msg.index).setLevel(nbt, Ability.ARMOR_ABILITIES.get(msg.index).getLevel(nbt) + 1);
									Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.ARMOR_ABILITIES.get(msg.index).getTier());
								}
								else
								{
									Ability.ARMOR_ABILITIES.get(msg.index).addAbility(nbt);
									if(!player.isCreative())
										player.giveExperienceLevels(-Ability.ARMOR_ABILITIES.get(msg.index).getExpLevel(nbt) + 1);
								}
							}
						}
					}
				});
	}
}