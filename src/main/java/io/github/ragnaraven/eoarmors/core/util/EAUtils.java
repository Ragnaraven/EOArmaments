package io.github.ragnaraven.eoarmors.core.util;

import java.util.List;
import java.util.UUID;

import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class EAUtils
{
	public static boolean canEnhance(Item item)
	{
		if(ConfigHolder.SERVER.onlyModdedItems.get())
			if(item == Items.IRON_SWORD || item == Items.IRON_AXE || item == Items.IRON_HOE || item == Items.IRON_BOOTS || item == Items.IRON_CHESTPLATE || item == Items.IRON_HELMET || item == Items.IRON_LEGGINGS
					|| item == Items.DIAMOND_AXE || item == Items.DIAMOND_HOE || item == Items.DIAMOND_SWORD || item == Items.DIAMOND_BOOTS || item == Items.DIAMOND_CHESTPLATE || item == Items.DIAMOND_HELMET || item == Items.DIAMOND_LEGGINGS
					|| item == Items.GOLDEN_AXE || item == Items.GOLDEN_HOE || item == Items.GOLDEN_SWORD || item == Items.GOLDEN_BOOTS || item == Items.GOLDEN_CHESTPLATE || item == Items.GOLDEN_HELMET || item == Items.GOLDEN_LEGGINGS
					|| item == Items.STONE_AXE || item == Items.STONE_HOE || item == Items.STONE_SWORD
					|| item == Items.WOODEN_AXE || item == Items.WOODEN_HOE || item == Items.WOODEN_SWORD
					|| item == Items.BOW
					|| item == Items.CHAINMAIL_BOOTS || item == Items.CHAINMAIL_CHESTPLATE || item == Items.CHAINMAIL_HELMET || item == Items.CHAINMAIL_LEGGINGS)
				return false;

		if(ConfigHolder.SERVER.extraItems.get().size() != 0)
		{
			boolean allowed=false;
			for(int k = 0; k < ConfigHolder.SERVER.extraItems.get().size(); k++)
				if(ConfigHolder.SERVER.extraItems.get().get(k).equals(item.getRegistryName().getPath()))
					allowed=true;
			return allowed || item instanceof SwordItem || item instanceof AxeItem || item instanceof HoeItem || item instanceof BowItem || item instanceof ArmorItem;
		}
		else
			return item instanceof SwordItem || item instanceof AxeItem || item instanceof HoeItem || item instanceof BowItem || item instanceof ArmorItem;
	}
	
	public static boolean canEnhanceWeapon(Item item)
	{
		return canEnhance(item) && !(item instanceof ArmorItem);
	}
	
	public static boolean canEnhanceMelee(Item item)
	{
		return canEnhance(item) && !(item instanceof ArmorItem) && !(item instanceof BowItem);
	}
	
	public static boolean canEnhanceRanged(Item item)
	{
		return canEnhance(item) && item instanceof BowItem;
	}
	
	public static boolean canEnhanceArmor(Item item)
	{
		return canEnhance(item) && item instanceof ArmorItem;
	}
	
	public static boolean isDamageSourceAllowed(DamageSource damageSource)
	{
		return !(damageSource == DamageSource.FALL ||
				damageSource == DamageSource.DROWN ||
				damageSource == DamageSource.CACTUS ||
				damageSource == DamageSource.STARVE ||
				damageSource == DamageSource.IN_WALL ||
				damageSource == DamageSource.IN_FIRE ||
				damageSource == DamageSource.OUT_OF_WORLD) || damageSource.getDirectEntity() instanceof LivingEntity;
	}
	
	public static Entity getEntityByUniqueId(UUID uniqueId)
	{
		return ((ServerLevel) Minecraft.getInstance().player.getCommandSenderWorld()).getEntity(uniqueId);

		//TODO: CRASH CHECK IF SERVER WORLD FAILS?

	    /*for (Entity entity : Minecraft.getInstance().player.getCommandSenderWorld().getEntityByUuid())
	    	{
	    		if (entity.getUUID().equals(uniqueId))
	                return entity;
	    	}

	    return null;*/
	}
	
	public static boolean containsString (List<Component> tooltip, String string)
	{
		if(tooltip.size() <= 0) return false;
		
		for(int i=0; i<tooltip.size(); i++)
		{
			if(tooltip.get(i).getString().equals(string))
				return true;
		}
		return false;
	}
	
	public static int lineContainsString (List<Component> tooltip, String string)
	{
		if(tooltip.size() <= 0) return -1;
		
		for(int i=0; i<tooltip.size(); i++)
		{
			if(tooltip.get(i).getString().equals(string))
				return i;
		}
		return -1;
	}
}