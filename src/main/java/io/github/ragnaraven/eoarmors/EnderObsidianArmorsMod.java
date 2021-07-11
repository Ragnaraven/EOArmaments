package io.github.ragnaraven.eoarmors;

import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmors.common.items.EOAItems;
import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.loot.EOALootRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Random;

@Mod(EnderObsidianArmorsMod.MODID)
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID)
public class EnderObsidianArmorsMod
{
    public static final String MODID = "eoarmors";
	
	public static final Random RANDOM = new Random();

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
		bus.addListener(this::setup);

		EOAItems.ITEMS.register(bus);
		EOABlocks.BLOCKS.register(bus);

		EOALootRegistry.init();

		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);


		//MinecraftForge.EVENT_BUS.register(EAOGeneral.class);
	}

	private void setup(final FMLCommonSetupEvent event)
	{

	}
}
