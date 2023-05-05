package io.github.ragnaraven.eoarmaments.common.armor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import static io.github.ragnaraven.eoarmaments.core.util.EOAHelpers.CHECK_ARMOR;
import static io.github.ragnaraven.eoarmaments.core.util.EOAHelpers.LEVEL_OBSIDIAN;

/**
 * Created by Ragnaraven on 1/21/2017 at 9:54 PM.
 */
@Mod.EventBusSubscriber
public class ArmorItemObsidian extends ModArmorItem
{
	public ArmorItemObsidian(String name, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlotType, Properties properties)
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

		if(armor != LEVEL_OBSIDIAN)
			return; //-1 means no match for set.

		//Fire res
		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 0, 1)); //40 ticks will prevent the ability from failing

		//Strength
		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 0, 0)); //40 ticks will prevent the ability from failing
	}
}