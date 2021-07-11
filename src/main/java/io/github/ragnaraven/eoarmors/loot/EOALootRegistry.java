package io.github.ragnaraven.eoarmors.loot;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EOALootRegistry
{
    public static LootFunctionType CUSTOMIZE_TO_EO_MINING;

    private static LootFunctionType register(String resLocString, ILootSerializer<? extends ILootFunction> iLootSerializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(resLocString), new LootFunctionType(iLootSerializer));
    }

    public static void init(){
        CUSTOMIZE_TO_EO_MINING = register(EnderObsidianArmorsMod.MODID + ":", new CustomizeToEOMining.Serializer());
    }



}
