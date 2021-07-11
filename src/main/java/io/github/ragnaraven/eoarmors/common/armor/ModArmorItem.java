package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.common.items.EOAItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ModArmorItem extends ArmorItem
{
    protected String name;

    public ModArmorItem(String name, IArmorMaterial armorMaterial, EquipmentSlotType equipmentSlotType, Item.Properties properties)
    {
        super(armorMaterial, equipmentSlotType, properties);

        this.name = name;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        if(slot == EquipmentSlotType.LEGS)
            return EnderObsidianArmorsMod.MODID + ":textures/models/armor/armor_" + name + "_layer_1.png";
        else
            return EnderObsidianArmorsMod.MODID + ":textures/models/armor/armor_" + name + "_layer_0.png";
    }
    /*
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(new TranslationTextComponent("dragon." + eggType.toString().toLowerCase()).mergeStyle(eggType.color));
        tooltip.add(new TranslationTextComponent("item.dragonscales_armor.desc").mergeStyle(TextFormatting.GRAY));
    }*/
}
