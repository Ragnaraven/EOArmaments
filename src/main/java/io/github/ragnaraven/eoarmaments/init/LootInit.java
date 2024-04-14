package io.github.ragnaraven.eoarmaments.init;

import com.mojang.serialization.Codec;
import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.loot.CustomizeToEOMining;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class LootInit {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EnderObsidianArmorsMod.MOD_ID);

    public static final RegistryObject<Codec<CustomizeToEOMining>> CUSTOMIZE_TO_EO_MINING = LOOT_MODIFIERS.register("eoa_globallootmodifier", () -> CustomizeToEOMining.CODEC);
}
