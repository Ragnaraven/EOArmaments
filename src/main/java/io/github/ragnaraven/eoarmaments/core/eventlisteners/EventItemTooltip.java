package io.github.ragnaraven.eoarmaments.core.eventlisteners;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;
import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import io.github.ragnaraven.eoarmaments.core.essentials.Ability;
import io.github.ragnaraven.eoarmaments.core.essentials.Experience;
import io.github.ragnaraven.eoarmaments.core.essentials.Rarity;
import io.github.ragnaraven.eoarmaments.core.util.EAUtils;
import io.github.ragnaraven.eoarmaments.core.util.NBTHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Displays information about the weapon when hovered over in an inventory.
 *
 */
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID)
public class EventItemTooltip
{
	/**
	 * Gets called whenever the tooltip for an item needs to appear.
	 * @param event
	 */
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void addInformation(ItemTooltipEvent event)
	{
		List<Component> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();
		Item item = stack.getItem();

		if (EAUtils.canEnhance(item))
		{
			CompoundTag nbt = NBTHelper.loadStackNBT(stack);

			if (Experience.isEnabled(nbt))
			{
				Rarity rarity = Rarity.getRarity(nbt);
				int level = Experience.getLevel(nbt);
				int experience = Experience.getExperience(nbt);
				int maxExperience = Experience.getMaxLevelExp(level);

				changeTooltips(tooltip, stack, rarity);

			// add tooltips

				// level
				if (level >= ConfigHolder.SERVER.maxLevel.get())
					tooltip.add(new TextComponent(I18n.get("eoarmaments.misc.level") + ": " + ChatFormatting.RED + I18n.get("eoarmaments.misc.max")));
				else
					tooltip.add(new TextComponent(I18n.get("eoarmaments.misc.level") + ": " + ChatFormatting.WHITE + level));

				// experience
				if (level >= ConfigHolder.SERVER.maxLevel.get())
					tooltip.add(new TextComponent(I18n.get("eoarmaments.misc.experience") + ": " + I18n.get("eoarmaments.misc.max")));
				else
					tooltip.add(new TextComponent(I18n.get("eoarmaments.misc.experience") + ": " + experience + " / " + maxExperience));

				// durability
				if (ConfigHolder.SERVER.showDurabilityInTooltip.get())
				{
					tooltip.add(new TextComponent(I18n.get("eoarmaments.misc.durability") + ": " + (stack.getMaxDamage() - stack.getDamageValue()) + " / " + stack.getMaxDamage()));
				}

				// abilities
				tooltip.add(new TextComponent(""));
				if (Screen.hasShiftDown())
				{
					tooltip.add(new TextComponent(rarity.getColor() + "" + ChatFormatting.ITALIC + I18n.get("eoarmaments.misc.abilities")));
					tooltip.add(new TextComponent(""));

					if (EAUtils.canEnhanceWeapon(item))
					{
						for (Ability ability : Ability.WEAPON_ABILITIES)
						{
							if (ability.hasAbility(nbt))
							{
								tooltip.add(new TranslatableComponent("-" + ability.getColor() + ability.getName(nbt)));
							}
						}
					}
					else if (EAUtils.canEnhanceArmor(item))
					{
						for (Ability ability : Ability.ARMOR_ABILITIES)
						{
							if (ability.hasAbility(nbt))
							{
								tooltip.add(new TranslatableComponent("-" + ability.getColor() + ability.getName(nbt)));
							}
						}
					}
				}
				else
					tooltip.add(new TextComponent(rarity.getColor() + "" + ChatFormatting.ITALIC + I18n.get("eoarmaments.misc.abilities.shift")));
			}
		}
	}
	
	private static void changeTooltips(List<Component> tooltip, ItemStack stack, Rarity rarity)
	{
		// rarity after the name
		tooltip.set(0, new TextComponent(stack.getDisplayName().getString() + rarity.getColor() + " (" + ChatFormatting.ITALIC + I18n.get("eoarmaments.rarity." + rarity.getName()) + ")"));
		
		if (EAUtils.containsString(tooltip, "When in main hand:") && !(stack.getItem() instanceof BowItem))
		{
			Multimap<Attribute, AttributeModifier> map = stack.getItem().getAttributeModifiers(EquipmentSlot.MAINHAND.MAINHAND, stack);
			Collection<AttributeModifier> damageCollection = map.get(Attributes.ATTACK_DAMAGE);
			AttributeModifier damageModifier = (AttributeModifier) damageCollection.toArray()[0];
			double damage = ((damageModifier.getAmount() + 1) * rarity.getEffect()) + damageModifier.getAmount() + 1;
			String d = String.format("%.1f", damage);
			
			if(rarity.getEffect() != 0)
				tooltip.set(EAUtils.lineContainsString(tooltip, "When in main hand:") + 2, new TextComponent(rarity.getColor() + " " + d + ChatFormatting.GRAY +" "+ I18n.get("eoarmaments.misc.tooltip.attackdamage")));
		}
		
		if (EAUtils.containsString(tooltip, "When on head:") || EAUtils.containsString(tooltip, "When on body:") || EAUtils.containsString(tooltip, "When on legs:") || EAUtils.containsString(tooltip, "When on feet:"))
		{
			String p = String.format("%.1f", 100-(100/(1.0F + (rarity.getEffect()/5F))));
			float percentage = Float.valueOf(p);
			int line = 2;
			if(EAUtils.containsString(tooltip, "When on head:")) line = EAUtils.lineContainsString(tooltip, "When on head:");
			if(EAUtils.containsString(tooltip, "When on body:")) line = EAUtils.lineContainsString(tooltip, "When on body:");
			if(EAUtils.containsString(tooltip, "When on legs:")) line = EAUtils.lineContainsString(tooltip, "When on legs:");
			if(EAUtils.containsString(tooltip, "When on feet:")) line = EAUtils.lineContainsString(tooltip, "When on feet:");
			if(percentage != 0)
				tooltip.add(line + 1, new TextComponent(" " + ChatFormatting.BLUE + "+" + rarity.getColor() + percentage + ChatFormatting.BLUE + "% " + I18n.get("eoarmaments.misc.rarity.armorreduction")));
		}
		
		if(EAUtils.canEnhanceRanged(stack.getItem()) && rarity.getEffect() != 0)
		{
			String b = String.format("%.1f", rarity.getEffect()/3*100);
			tooltip.add(1, new TextComponent(I18n.get("eoarmaments.misc.rarity.arrowpercentage") + " " + rarity.getColor() + "+" + b + "%"));
		}
	}
}