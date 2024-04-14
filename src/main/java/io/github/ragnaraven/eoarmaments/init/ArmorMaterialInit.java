package io.github.ragnaraven.eoarmaments.init;

import io.github.ragnaraven.eoarmaments.common.armor.ModArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ArmorMaterialInit {
    public static final ModArmorMaterial EMERALD = new ModArmorMaterial(
            new int[] { 500, 1200, 600, 400 },
            new int[] { 4, 9, 8, 4 },
            8,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(Items.EMERALD),
            "emerald",
            2.0f,
            1.0f
    );

    public static final ModArmorMaterial OBSIDIAN = new ModArmorMaterial(
            new int[] { 100, 100, 100, 100 },
            new int[] { 4, 4, 4, 4 },
            10,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Items.OBSIDIAN),
            "obsidian",
            0.5f,
            0.0f
    );

    public static final ModArmorMaterial ENDER_OBSIDIAN = new ModArmorMaterial(
            new int[] { 100, 100, 100, 100 },
            new int[] { 2, 2, 2, 2 },
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(BlockInit.ENDER_OBSIDIAN.get()),
            "ender_obsidian",
            0.5f,
            0.0f
    );
}
