package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EOATabs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

/**
 * Created by Ragnaraven on 1/20/2017 at 6:02 PM.
 */
public class EOAArmorSuit
{
	protected String name;
	protected ArmorMaterial armorMaterial;

	protected ArmorItem head;
	protected ArmorItem chest;
	protected ArmorItem legs;
	protected ArmorItem feet;

	public static final String text_head= "head";
	public static final String text_chest = "chest";
	public static final String text_legs = "legs";
	public static final String text_feet = "feet";

	public ArmorItem head () { return head; }
	public String name_head () { return name + "_" + text_head; }

	public ArmorItem chest () { return chest; }
	public String name_chest() { return name + "_" + text_chest; }

	public ArmorItem legs () { return legs; }
	public String name_legs() { return name + "_" + text_legs; }

	public ArmorItem feet () { return feet; }
	public String name_feet() { return name + "_" + text_feet; }

	protected EOAArmorSuit () {}

	public EOAArmorSuit(String name, ArmorMaterial armorMaterial)
	{
		this.name = "armor_" + name;
		this.armorMaterial = armorMaterial;

		head   = new ModArmorItem(name, armorMaterial, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
		chest  = new ModArmorItem(name, armorMaterial, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
		legs   = new ModArmorItem(name, armorMaterial, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
		feet   = new ModArmorItem(name, armorMaterial, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
	}
}