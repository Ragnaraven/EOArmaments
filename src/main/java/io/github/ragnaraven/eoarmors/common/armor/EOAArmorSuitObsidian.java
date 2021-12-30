package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EOATabs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class EOAArmorSuitObsidian extends EOAArmorSuit
{
    public EOAArmorSuitObsidian(String name, ArmorMaterial armorMaterial)
    {
        this.name = "armor_" + name;
        this.armorMaterial = armorMaterial;

        head   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        chest  = new ArmorItemObsidian(name, armorMaterial, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        legs   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        feet   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
    }
}
