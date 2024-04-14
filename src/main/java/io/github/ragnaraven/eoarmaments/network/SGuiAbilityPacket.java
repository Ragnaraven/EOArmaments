package io.github.ragnaraven.eoarmaments.network;

import java.util.function.Supplier;

import io.github.ragnaraven.eoarmaments.core.essentials.Ability;
import io.github.ragnaraven.eoarmaments.core.essentials.Experience;
import io.github.ragnaraven.eoarmaments.core.util.EAUtils;
import io.github.ragnaraven.eoarmaments.core.util.NBTHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SGuiAbilityPacket {
	private final int index;

	public SGuiAbilityPacket(int index) {
		this.index = index;
	}

	public static void encode(SGuiAbilityPacket msg, FriendlyByteBuf buffer) {
		buffer.writeInt(msg.index);
	}

	public static SGuiAbilityPacket decode(FriendlyByteBuf buf) {
		return new SGuiAbilityPacket(buf.readInt());
	}

	public void handle(CustomPayloadEvent.Context context) {
		context.enqueueWork
				(() -> {
					Player player = context.getSender();

					if (player != null) {
						ItemStack stack = player.getMainHandItem();

						if (stack != ItemStack.EMPTY) {
							CompoundTag nbt = NBTHelper.loadStackNBT(stack);

							if (EAUtils.canEnhanceWeapon(stack.getItem())) {
								if (Ability.WEAPON_ABILITIES.get(this.index).hasAbility(nbt)) {
									Ability.WEAPON_ABILITIES.get(this.index).setLevel(nbt, Ability.WEAPON_ABILITIES.get(this.index).getLevel(nbt) + 1);
									Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.WEAPON_ABILITIES.get(this.index).getTier());
								} else {
									Ability.WEAPON_ABILITIES.get(this.index).addAbility(nbt);
									if (!player.isCreative())
										player.giveExperienceLevels(-Ability.WEAPON_ABILITIES.get(this.index).getExpLevel(nbt) + 1);
								}
							} else if (EAUtils.canEnhanceArmor(stack.getItem())) {
								if (Ability.ARMOR_ABILITIES.get(this.index).hasAbility(nbt)) {
									Ability.ARMOR_ABILITIES.get(this.index).setLevel(nbt, Ability.ARMOR_ABILITIES.get(this.index).getLevel(nbt) + 1);
									Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.ARMOR_ABILITIES.get(this.index).getTier());
								} else {
									Ability.ARMOR_ABILITIES.get(this.index).addAbility(nbt);
									if (!player.isCreative())
										player.giveExperienceLevels(-Ability.ARMOR_ABILITIES.get(this.index).getExpLevel(nbt) + 1);
								}
							}
						}
					}
				});
	}
}