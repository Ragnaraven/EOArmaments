package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EOATabs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class EOAArmorSuitEnderObsidian extends EOAArmorSuit
{
    public EOAArmorSuitEnderObsidian(String name, ArmorMaterial armorMaterial)
    {
        this.name = "armor_" + name;
        this.armorMaterial = armorMaterial;

        head   = new ArmorItemEnderObsidian(name, armorMaterial, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        chest  = new ArmorItemEnderObsidian(name, armorMaterial, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        legs   = new ArmorItemEnderObsidian(name, armorMaterial, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
        feet   = new ArmorItemEnderObsidian(name, armorMaterial, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL));
    }
}
