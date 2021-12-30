package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.loot.CustomizeToEOMining;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EOARegistry
{
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new CustomizeToEOMining.Serializer().setRegistryName(EnderObsidianArmorsMod.MODID, "harvest_drops_event"));
    }


}
