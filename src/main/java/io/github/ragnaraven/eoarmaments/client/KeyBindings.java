package io.github.ragnaraven.eoarmaments.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class KeyBindings
{
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings() {}

    private static final String CATEGORY = "key.categories." + EnderObsidianArmorsMod.MOD_ID;
    public final KeyMapping abilityKey = new KeyMapping(
            "key." +  EnderObsidianArmorsMod.MOD_ID + ".gui.weapon_interface",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_K, -1),
            CATEGORY
    );
}
