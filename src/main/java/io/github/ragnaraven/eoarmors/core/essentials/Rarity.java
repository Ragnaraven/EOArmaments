package io.github.ragnaraven.eoarmors.core.essentials;

import java.util.Random;

import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.config.ServerConfig;
import io.github.ragnaraven.eoarmors.core.util.RandomCollection;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;

public enum Rarity 
{
	DEFAULT("", 0, 0.0, 0.0),
	BASIC(ChatFormatting.WHITE, 0xFFFFFF, ConfigHolder.SERVER.basicChance.get(), ConfigHolder.SERVER.basicDamage.get()),
	UNCOMMON(ChatFormatting.DARK_GREEN, 0x00AA00, ConfigHolder.SERVER.uncommonChance.get(), ConfigHolder.SERVER.uncommonDamage.get()),
	RARE(ChatFormatting.AQUA, 0x55FFFF, ConfigHolder.SERVER.rareChance.get(), ConfigHolder.SERVER.rareDamage.get()),
	ULTRA_RARE(ChatFormatting.DARK_PURPLE, 0xAA00AA, ConfigHolder.SERVER.ultraRareChance.get(), ConfigHolder.SERVER.ultraRareDamage.get()),
	LEGENDARY(ChatFormatting.GOLD, 0xFFAA00, ConfigHolder.SERVER.legendaryChance.get(), ConfigHolder.SERVER.legendaryDamage.get()),
	ARCHAIC(ChatFormatting.LIGHT_PURPLE, 0xFF55FF, ConfigHolder.SERVER.archaicChance.get(), ConfigHolder.SERVER.archaicDamage.get());
	
	private String color;
	private int hex;
	private double weight;
	private double effect;
	private static final Rarity[] RARITIES = Rarity.values();
	private static final RandomCollection<Rarity> RANDOM_RARITIES = new RandomCollection<Rarity>();
	
	Rarity(Object color, int hex, double weight, double effect)
	{
		this.color = color.toString();
		this.hex = hex;
		this.weight = weight;
		this.effect = effect;
	}
	
	/**
	 * Returns one of the enums above, according to their weight.
	 * @param random
	 * @return
	 */
	public static Rarity getRandomRarity(Random random)
	{
		return RANDOM_RARITIES.next(random);
	}

	/**
	 * Retrieves the rarity applied.
	 * @param nbt
	 * @return
	 */
	public static Rarity getRarity(CompoundTag nbt)
	{
		return nbt != null && nbt.contains("RARITY") ? RARITIES[nbt.getInt("RARITY")] : DEFAULT;
	}
	
	public void setRarity(CompoundTag nbt)
	{
		if (nbt != null)
		{
			nbt.putInt("RARITY", ordinal());
		}
	}
	
	public static void setRarity(CompoundTag nbt, String rarityName)
	{
		int rarity = Integer.parseInt(rarityName);
		nbt.putInt("RARITY", rarity);
	}

	public String getName()
	{
		return this.toString();
	}
	
	public String getColor()
	{
		return color;
	}
	
	public int getHex()
	{
		return hex;
	}
	
	public double getEffect()
	{
		return effect;
	}

	static
	{
		for (Rarity rarity : RARITIES)
		{
			if (rarity.weight > 0.0D)
			{
				RANDOM_RARITIES.add(rarity.weight, rarity);
			}
		}
	}
}