package io.github.ragnaraven.eoarmaments;

import io.github.ragnaraven.eoarmaments.client.KeyBindings;
import io.github.ragnaraven.eoarmaments.init.BlockInit;
import io.github.ragnaraven.eoarmaments.init.CreativeTabsInit;
import io.github.ragnaraven.eoarmaments.init.ItemInit;
import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import io.github.ragnaraven.eoarmaments.init.LootInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(EnderObsidianArmorsMod.MOD_ID)
public class EnderObsidianArmorsMod
{
    public static final String MOD_ID = "eoarmaments";
	
	public static final Random RANDOM = new Random();
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	/*
	public static boolean BUILDCRAFT = false;
	public static boolean IC2 = false;
	public static boolean COPPER = false;
	public static boolean TIN = false;
	public static boolean SILVER = false;
	public static boolean REFINED_IRON = false;
	 */

	public EnderObsidianArmorsMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		CreativeTabsInit.TABS.register(bus);
		LootInit.LOOT_MODIFIERS.register(bus);

		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
	}
}
