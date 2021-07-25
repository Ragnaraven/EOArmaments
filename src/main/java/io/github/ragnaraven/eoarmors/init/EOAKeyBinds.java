package io.github.ragnaraven.eoarmors.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class EOAKeyBinds
{
    public static KeyBinding abilityKey = new KeyBinding("key.gui.weapon_interface", 75, "key.eoarmors");

    public static void init(final FMLClientSetupEvent event)
    {
        ClientRegistry.registerKeyBinding(abilityKey);
    }
}
