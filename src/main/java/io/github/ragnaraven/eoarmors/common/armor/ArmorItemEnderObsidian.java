package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


import static io.github.ragnaraven.eoarmors.core.util.EOAHelpers.*;

/**
 * Created by Ragnaraven on 7/15/2017 at 8:16 PM.
 */
public class ArmorItemEnderObsidian extends ModArmorItem
{
	public ArmorItemEnderObsidian(String name, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlotType, Item.Properties properties)
	{
		super(name, armorMaterial, equipmentSlotType, properties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return false;
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player)
	{
		super.onArmorTick(stack, world, player);

		int armor = CHECK_ARMOR(player);

		if(armor != LEVEL_ENDER_OBSIDIAN)
			return; //-1 means no match for set.

		//Both sets get fire resistance
		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 0, 1)); //40 ticks will prevent the ability from failing

		//Add haste if full set plus pick
		try
		{
			if(CHECK_ARMOR_SET_AGAINST_HELD_TOOLS(armor, player))
				player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 0, 0)); //40 ticks will prevent the ability from failing
		}
		catch (NullPointerException ignored) { }

		player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0)); //40 ticks will prevent the ability from failing

		if(!player.getCommandSenderWorld().isClientSide())
		{
			//Occasional Ender particles
			/*if(EnderObsidianArmorsMod.RANDOM.nextInt(5) == 0)
				.spawnEnderObsidianParticles(player, 1);*/
		}
	}

}