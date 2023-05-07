package io.github.ragnaraven.eoarmaments.core.eventlisteners;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.loot.CustomizeToEOMining;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EOARegistry
{
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new CustomizeToEOMining.Serializer().setRegistryName(EnderObsidianArmorsMod.MODID, "eoa_globallootmodifier"));
    }
}
