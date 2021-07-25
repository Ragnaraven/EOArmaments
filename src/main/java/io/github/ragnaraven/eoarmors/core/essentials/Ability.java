package io.github.ragnaraven.eoarmors.core.essentials;

import java.util.ArrayList;

import io.github.ragnaraven.eoarmors.config.Config;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum Ability
{
		// weapon
	// active
	FIRE("weapon", "active", Config.fireAbility, TextFormatting.RED, 0xFF5555, 1, 3),
	FROST("weapon", "active", Config.frostAbility, TextFormatting.AQUA, 0x55FFFF, 1, 3),
	POISON("weapon", "active", Config.poisonAbility, TextFormatting.DARK_GREEN, 0x00AA00, 1, 3),
	INNATE("weapon", "active", Config.innateAbility, TextFormatting.DARK_RED, 0xAA0000, 2, 3),
	BOMBASTIC("weapon", "active", Config.bombasticAbility, TextFormatting.GRAY, 0xAAAAAA, 3, 3),
	CRITICAL_POINT("weapon", "active", Config.criticalpointAbility, TextFormatting.DARK_GRAY, 0x555555, 3, 3),
	// passive
	ILLUMINATION("weapon", "passive", Config.illuminationAbility, TextFormatting.YELLOW, 0xFFFF55, 2, 1),
	ETHEREAL("weapon", "passive", Config.etherealAbility, TextFormatting.GREEN, 0x55FF55, 2, 2),
	BLOODTHIRST("weapon", "passive", Config.bloodthirstAbility, TextFormatting.DARK_PURPLE, 0xAA00AA, 3, 2),
	
		// armor
	// active
	MOLTEN("armor", "active", Config.moltenAbility, TextFormatting.RED, 0xFF5555, 2, 2),
	FROZEN("armor", "active", Config.frozenAbility, TextFormatting.AQUA, 0x55FFFF, 2, 2),
	TOXIC("armor", "active", Config.toxicAbility, TextFormatting.DARK_GREEN, 0x00AA00, 2, 2),
	// passive
	BEASTIAL("armor", "passive", Config.beastialAbility, TextFormatting.DARK_RED, 0xAA0000, 2, 1),
	REMEDIAL("armor", "passive", Config.remedialAbility, TextFormatting.LIGHT_PURPLE, 0xFF55FF, 2, 2),
	HARDENED("armor", "passive", Config.hardenedAbility, TextFormatting.GRAY, 0xAAAAAA, 3, 1),
	ADRENALINE("armor", "passive", Config.adrenalineAbility, TextFormatting.GREEN, 0x55FF55, 3, 1);
	
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
	public boolean hasAbility(CompoundNBT nbt)
	{
		return nbt != null && nbt.getInt(toString()) > 0;
	}
	
	/**
	 * Adds the specified ability to the stack.
	 * @param nbt
	 */
	public void addAbility(CompoundNBT nbt)
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
	public void removeAbility(CompoundNBT nbt)
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
	 * @param ability
	 * @return
	 */
	public boolean hasEnoughExp (PlayerEntity player, CompoundNBT nbt)
	{
		return getExpLevel(nbt) <= player.experienceLevel || player.isCreative();
	}
	
	/**
	 * Returns the abilitys requiring experience level.
	 * @param ability
	 * @param nbt
	 * @return
	 */
	public int getExpLevel (CompoundNBT nbt)
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
	public void setLevel(CompoundNBT nbt, int level)
	{
		nbt.putInt(toString(), level);
	}
	
	/**
	 * Returns the level of the specified ability.
	 * @param nbt
	 * @return
	 */
	public int getLevel(CompoundNBT nbt)
	{
		if (nbt != null) return nbt.getInt(toString());
		else return 0;
	}
	
	public boolean canUpgradeLevel(CompoundNBT nbt)
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
	
	public String getName(CompoundNBT nbt)
	{
		if (getLevel(nbt) == 2)
			return new TranslationTextComponent("enhancedarmaments.ability." + this.toString()).getString() + " II";
		else if (getLevel(nbt) == 3)
			return new TranslationTextComponent("enhancedarmaments.ability." + this.toString()).getString() + " III";
		else
			return new TranslationTextComponent("enhancedarmaments.ability." + this.toString()).getString();
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getTypeName()
	{
		return new TranslationTextComponent("enhancedarmaments.ability.type." + type.toString()).getString(); 
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