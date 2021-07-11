package io.github.ragnaraven.eoarmors.common.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import static io.github.ragnaraven.eoarmors.core.util.EOAHelpers.CHECK_ARMOR;
import static io.github.ragnaraven.eoarmors.core.util.EOAHelpers.LEVEL_OBSIDIAN;

/**
 * Created by Ragnaraven on 1/21/2017 at 9:54 PM.
 */
@Mod.EventBusSubscriber
public class ArmorItemObsidian extends ModArmorItem
{
	public ArmorItemObsidian(String name, IArmorMaterial armorMaterial, EquipmentSlotType equipmentSlotType, Item.Properties properties)
	{
		super(name, armorMaterial, equipmentSlotType, properties);
	}

	@Override
	public boolean isValidRepairItem(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return false;
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
	{
		super.onArmorTick(stack, world, player);

		int armor = CHECK_ARMOR(player);

		if(armor != LEVEL_OBSIDIAN)
			return; //-1 means no match for set.

		//Fire res
		player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 0, 1)); //40 ticks will prevent the ability from failing

		//Strength
		player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 0, 0)); //40 ticks will prevent the ability from failing
	}
}