package io.github.ragnaraven.eoarmaments.common.armor;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModArmorItem extends ArmorItem
{
    protected String name;

    public ModArmorItem(String name, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlotType, Properties properties)
    {
        super(armorMaterial, equipmentSlotType, properties);

        this.name = name;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type)
    {
        if(slot == EquipmentSlot.LEGS)
            return EnderObsidianArmorsMod.MODID + ":textures/models/armor/" + name + "_layer_2.png";
        else
            return EnderObsidianArmorsMod.MODID + ":textures/models/armor/" + name + "_layer_1.png";
    }
    /*
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new TranslationTextComponent("dragon." + eggType.toString().toLowerCase()).mergeStyle(eggType.color));
        tooltip.add(new TranslationTextComponent("item.dragonscales_armor.desc").mergeStyle(TextFormatting.GRAY));
    }*/
}
