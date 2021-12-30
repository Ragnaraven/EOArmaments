package io.github.ragnaraven.eoarmors.init;

import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.KeyMapping;

public class EOAKeyBinds
{
    public static KeyMapping abilityKey = new KeyMapping("key.gui.weapon_interface", 75, "key.eoarmors");

    public static void init(final FMLClientSetupEvent event)
    {
        ClientRegistry.registerKeyBinding(abilityKey);
    }
}
