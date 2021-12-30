package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.loot.CustomizeToEOMining;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EOALootRegistry
{
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        System.out.println("PINGGGG5");

        event.getRegistry().register(new CustomizeToEOMining.Serializer().setRegistryName(EnderObsidianArmorsMod.MODID, "harvest_drops_event"));
    }
/*
    public static LootFunctionType CUSTOMIZE_TO_EO_MINING;

    private static LootFunctionType register(String resLocString, ILootSerializer<? extends ILootFunction> iLootSerializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(resLocString), new LootFunctionType(iLootSerializer));
    }

    public static void init(){
        CUSTOMIZE_TO_EO_MINING = register(EnderObsidianArmorsMod.MODID + ":", new CustomizeToEOMining.Serializer());*/
}
