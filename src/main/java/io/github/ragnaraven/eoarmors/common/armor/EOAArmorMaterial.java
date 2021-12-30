package io.github.ragnaraven.eoarmors.common.armor;

import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.sounds.SoundEvent;

import javax.swing.*;
import java.util.function.Supplier;

public enum EOAArmorMaterial implements ArmorMaterial
{

    /*Materials                                                                                                                                   iron                 15           2   6  5  2               9
    public static ItemArmor.ArmorMaterial emeraldMaterial =       EnumHelper.addArmorMaterial("Emerald Armor NEC",      "armor_emerald",       56, new int[]{4, 9, 8, 4},  8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f);
    public static ItemArmor.ArmorMaterial obsidianMaterial =      EnumHelper.addArmorMaterial("Obsidian Armor NEC",     "armor_obsidian",       3, new int[]{1, 1, 1, 1}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial enderObsidianMaterial = EnumHelper.addArmorMaterial("EnderObsidian Armor NEC","armor_ender_obsidian", 3, new int[]{1, 2, 2, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
            */
    EMERALD("emerald",               56, new int[]{4, 9, 8, 4}, 8, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 1.0F, () -> { return Ingredient.of(Items.EMERALD); } ),
    OBSIDIAN("obsidian",             3, new int[]{4, 4, 4, 4}, 10, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () -> { return Ingredient.of(Items.OBSIDIAN); } ),
    ENDER_OBSIDIAN("ender_obsidian", 3, new int[]{2, 2, 2, 2}, 15, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () -> { return Ingredient.of(EOABlocks.ENDER_OBSIDIAN.get()); } );

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private EOAArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections, int enchantmentValue,
                             SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    public int getDurabilityForSlot(EquipmentSlot equipmentSlotType) {
        return HEALTH_PER_SLOT[equipmentSlotType.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot equipmentSlotType) {
        return this.slotProtections[equipmentSlotType.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
