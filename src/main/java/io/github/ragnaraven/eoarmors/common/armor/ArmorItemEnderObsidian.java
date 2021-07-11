package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.client.render.particles.ParticleEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import static io.github.ragnaraven.eoarmors.core.util.EOAHelpers.*;

/**
 * Created by Ragnaraven on 7/15/2017 at 8:16 PM.
 */
public class ArmorItemEnderObsidian extends ModArmorItem
{
	public ArmorItemEnderObsidian(String name, IArmorMaterial armorMaterial, EquipmentSlotType equipmentSlotType, Item.Properties properties)
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

		if(armor != LEVEL_ENDER_OBSIDIAN)
			return; //-1 means no match for set.

		//Both sets get fire resistance
		player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 0, 1)); //40 ticks will prevent the ability from failing

		//Add haste if full set plus pick
		try
		{
			if(CHECK_ARMOR_SET_AGAINST_HELD_TOOLS(armor, player))
				player.addEffect(new EffectInstance(Effects.DIG_SPEED, 0, 0)); //40 ticks will prevent the ability from failing
		}
		catch (NullPointerException ignored) { }

		player.addEffect(new EffectInstance(Effects.NIGHT_VISION, 220, 0)); //40 ticks will prevent the ability from failing

		if(!player.getCommandSenderWorld().isClientSide())
		{
			//Occasional Ender particles
			if(EnderObsidianArmorsMod.RANDOM.nextInt(5) == 0)
				ParticleEffects.spawnEnderObsidianParticles(player, 1);
		}
	}

}