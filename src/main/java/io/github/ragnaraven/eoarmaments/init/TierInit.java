package io.github.ragnaraven.eoarmaments.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeTier;

public class TierInit {
    /*
        EMERALD(	    3, 5268, 9.5f,  5.50f, 12, () -> { return Ingredient.of(Items.EMERALD); } ),
		OBSIDIAN(		3, 500,  12.0f, 3.50f, 10, () -> { return Ingredient.of(Items.OBSIDIAN); } ),
		ENDER_OBSIDIAN( 3, 2500,  16f, 4.25f, 15, () -> { return Ingredient.of(EOAItems.ENDER_OBSIDIAN_PLATE.get()); } );

     */

    public static final ForgeTier EMERALD = new ForgeTier(
            4,
            5268,
            9.5f,
            4,
            12,
            ForgeHooks.getTagFromVanillaTier(Tiers.DIAMOND),
            () -> Ingredient.of(Items.EMERALD)
    );

    public static final ForgeTier OBSIDIAN = new ForgeTier(
            4,
            2500,
            15.0f,
            3.5f,
            10,
            ForgeHooks.getTagFromVanillaTier(Tiers.DIAMOND),
            () -> Ingredient.of(Items.OBSIDIAN)
    );

    public static final ForgeTier ENDER_OBSIDIAN = new ForgeTier(
            4,
            5000,
            128f,
            4.5f,
            20,
            ForgeHooks.getTagFromVanillaTier(Tiers.DIAMOND),
            () -> Ingredient.of(ItemInit.ENDER_OBSIDIAN_PLATE.get())
    );
}
