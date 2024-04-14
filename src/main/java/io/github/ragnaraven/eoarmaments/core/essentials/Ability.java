package io.github.ragnaraven.eoarmaments.core.essentials;

import java.util.ArrayList;

import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public enum Ability
{
		// weapon
	// active
	FIRE("weapon", "active", ConfigHolder.SERVER.fireAbility.get(), ChatFormatting.RED, 0xFF5555, 1, 3),
	FROST("weapon", "active", ConfigHolder.SERVER.frostAbility.get(), ChatFormatting.AQUA, 0x55FFFF, 1, 3),
	POISON("weapon", "active", ConfigHolder.SERVER.poisonAbility.get(), ChatFormatting.DARK_GREEN, 0x00AA00, 1, 3),
	INNATE("weapon", "active", ConfigHolder.SERVER.innateAbility.get(), ChatFormatting.DARK_RED, 0xAA0000, 2, 3),
	BOMBASTIC("weapon", "active", ConfigHolder.SERVER.bombasticAbility.get(), ChatFormatting.GRAY, 0xAAAAAA, 3, 3),
	CRITICAL_POINT("weapon", "active", ConfigHolder.SERVER.criticalpointAbility.get(), ChatFormatting.DARK_GRAY, 0x555555, 3, 3),
	// passive
	ILLUMINATION("weapon", "passive", ConfigHolder.SERVER.illuminationAbility.get(), ChatFormatting.YELLOW, 0xFFFF55, 2, 1),
	ETHEREAL("weapon", "passive", ConfigHolder.SERVER.etherealAbility.get(), ChatFormatting.GREEN, 0x55FF55, 2, 2),
	BLOODTHIRST("weapon", "passive", ConfigHolder.SERVER.bloodthirstAbility.get(), ChatFormatting.DARK_PURPLE, 0xAA00AA, 3, 2),
	
		// armor
	// active
	MOLTEN("armor", "active", ConfigHolder.SERVER.moltenAbility.get(), ChatFormatting.RED, 0xFF5555, 2, 2),
	FROZEN("armor", "active", ConfigHolder.SERVER.frozenAbility.get(), ChatFormatting.AQUA, 0x55FFFF, 2, 2),
	TOXIC("armor", "active", ConfigHolder.SERVER.toxicAbility.get(), ChatFormatting.DARK_GREEN, 0x00AA00, 2, 2),
	// passive
	BEASTIAL("armor", "passive", ConfigHolder.SERVER.beastialAbility.get(), ChatFormatting.DARK_RED, 0xAA0000, 2, 1),
	REMEDIAL("armor", "passive", ConfigHolder.SERVER.remedialAbility.get(), ChatFormatting.LIGHT_PURPLE, 0xFF55FF, 2, 2),
	HARDENED("armor", "passive", ConfigHolder.SERVER.hardenedAbility.get(), ChatFormatting.GRAY, 0xAAAAAA, 3, 1),
	ADRENALINE("armor", "passive", ConfigHolder.SERVER.adrenalineAbility.get(), ChatFormatting.GREEN, 0x55FF55, 3, 1);
	
	public static int WEAPON_ABILITIES_COUNT=0;
	public static int ARMOR_ABILITIES_COUNT=0;
	public static final ArrayList<Ability> WEAPON_ABILITIES = new ArrayList<Ability>();
	public static final ArrayList<Ability> ARMOR_ABILITIES = new ArrayList<Ability>();
	public static final ArrayList<Ability> ALL_ABILITIES = new ArrayList<Ability>();
	
	private String category;
	private String type;
	private boolean enabled;
	private String color;
	private int hex;
	private int tier;
	private int maxlevel;
	
	Ability(String category, String type, boolean enabled, Object color, int hex, int tier, int maxlevel)
	{
		this.category = category;
		this.type = type;
		this.enabled = enabled;
		this.color = color.toString();
		this.hex = hex;
		this.tier = tier;
		this.maxlevel = maxlevel;
	}
	
	/**
	 * Returns true if the stack has the ability.
	 * @param nbt
	 * @return
	 */
	public boolean hasAbility(CompoundTag nbt)
	{
		return nbt != null && nbt.getInt(toString()) > 0;
	}
	
	/**
	 * Adds the specified ability to the stack.
	 * @param nbt
	 */
	public void addAbility(CompoundTag nbt)
	{
		nbt.putInt(toString(), 1);
		if(nbt.contains("ABILITIES"))
			nbt.putInt("ABILITIES", nbt.getInt("ABILITIES")+1);
		else
			nbt.putInt("ABILITIES", 1);
	}
	
	/**
	 * Removes the specified ability from the NBT of the stack.
	 * @param nbt
	 */
	public void removeAbility(CompoundTag nbt)
	{
		nbt.remove(toString());
		if(nbt.contains("ABILITIES"))
			if(nbt.getInt("ABILITIES") > 0)
				nbt.putInt("ABILITIES", nbt.getInt("ABILITIES")-1);
	}
	
	/**
	 * Returns true if the player has enough experience to unlock the ability.
	 * @param nbt
	 * @param player
	 * @return
	 */
	public boolean hasEnoughExp (Player player, CompoundTag nbt)
	{
		return getExpLevel(nbt) <= player.experienceLevel || player.isCreative();
	}
	
	/**
	 * Returns the abilitys requiring experience level.
	 * @param nbt
	 * @return
	 */
	public int getExpLevel (CompoundTag nbt)
	{
		int requiredExpLevel=0;
		if(nbt.contains("ABILITIES"))
			requiredExpLevel = (getTier() + getMaxLevel()) * (nbt.getInt("ABILITIES") + 1) - 1;
		else
			requiredExpLevel = getTier() + getMaxLevel();
		return requiredExpLevel;
	}
	
	/**
	 * Sets the level of the specified ability.
	 * @param nbt
	 * @param level
	 */
	public void setLevel(CompoundTag nbt, int level)
	{
		nbt.putInt(toString(), level);
	}
	
	/**
	 * Returns the level of the specified ability.
	 * @param nbt
	 * @return
	 */
	public int getLevel(CompoundTag nbt)
	{
		if (nbt != null) return nbt.getInt(toString());
		else return 0;
	}
	
	public boolean canUpgradeLevel(CompoundTag nbt)
	{
		if (getLevel(nbt) < this.maxlevel)
			return true;
		else
			return false;
	}
	
	public int getTier()
	{
		return tier;
	}
	
	public int getMaxLevel()
	{
		return maxlevel;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public int getHex()
	{
		return hex;
	}
	
	public String getName()
	{
		return this.toString();
	}
	
	public String getName(CompoundTag nbt)
	{
		if (getLevel(nbt) == 2)
			return Component.translatable("eoarmaments.ability." + this.toString()).getString() + " II";
		else if (getLevel(nbt) == 3)
			return Component.translatable("eoarmaments.ability." + this.toString()).getString() + " III";
		else
			return Component.translatable("eoarmaments.ability." + this.toString()).getString();
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getTypeName()
	{
		return Component.translatable("eoarmaments.ability.type." + type.toString()).getString();
	}
	
	public String getCategory()
	{
		return category;
	}
	
	static
	{
		for (int i = 0; i < Ability.values().length; i++)
		{
			Ability.ALL_ABILITIES.add(Ability.values()[i]);
			if (Ability.values()[i].getCategory().equals("weapon") && Ability.values()[i].enabled)
			{
				Ability.WEAPON_ABILITIES.add(Ability.values()[i]);
				Ability.WEAPON_ABILITIES_COUNT++;
			}
			else if (Ability.values()[i].getCategory().equals("armor") && Ability.values()[i].enabled)
			{
				Ability.ARMOR_ABILITIES.add(Ability.values()[i]);
				Ability.ARMOR_ABILITIES_COUNT++;
			}
		}
	}
}