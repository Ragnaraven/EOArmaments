package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EOATabs;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;

public class EOAArmorSuitObsidian extends EOAArmorSuit
{
    public EOAArmorSuitObsidian(String name, IArmorMaterial armorMaterial)
    {
        this.name = "armor_" + name;
        this.armorMaterial = armorMaterial;

        head   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlotType.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        chest  = new ArmorItemObsidian(name, armorMaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        legs   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlotType.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        feet   = new ArmorItemObsidian(name, armorMaterial, EquipmentSlotType.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
    }
}
