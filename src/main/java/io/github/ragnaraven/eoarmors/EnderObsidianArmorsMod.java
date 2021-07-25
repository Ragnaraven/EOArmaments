package io.github.ragnaraven.eoarmors;

import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmors.common.items.EOAItems;
import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.config.ServerConfig;
import io.github.ragnaraven.eoarmors.core.eventlisteners.*;
import io.github.ragnaraven.eoarmors.init.EOAKeyBinds;
import io.github.ragnaraven.eoarmors.loot.EOALootRegistry;
import io.github.ragnaraven.eoarmors.network.PacketGuiAbility;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(EnderObsidianArmorsMod.MODID)
@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MODID)
public class EnderObsidianArmorsMod
{
    public static final String MODID = "eoarmors";
	
	public static final Random RANDOM = new Random();
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	/*
	public static boolean BUILDCRAFT = false;
	public static boolean IC2 = false;
	public static boolean COPPER = false;
	public static boolean TIN = false;
	public static boolean SILVER = false;
	public static boolean REFINED_IRON = false;
	 */

	//FROM EA
	private static final String PROTOCOL_VERSION = "1.0";
	public static SimpleChannel network = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(MODID, "networking"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();


	public EnderObsidianArmorsMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::setup);
		bus.addListener(this::clientInit);

		EOAItems.ITEMS.register(bus);
		EOABlocks.BLOCKS.register(bus);

		EOALootRegistry.init();

		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);

		//MinecraftForge.EVENT_BUS.register(EAOGeneral.class);
	}

	private void setup(FMLCommonSetupEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EventItemTooltip());
		MinecraftForge.EVENT_BUS.register(new EventLivingUpdate());
		MinecraftForge.EVENT_BUS.register(new EventInput());
		MinecraftForge.EVENT_BUS.register(new EventLivingHurt());
		MinecraftForge.EVENT_BUS.register(new EventLivingDeath());

		network.registerMessage(0, PacketGuiAbility.class, PacketGuiAbility::encode, PacketGuiAbility::decode, PacketGuiAbility::handle);
	}

	private void clientInit(FMLClientSetupEvent event)
	{
		EOAKeyBinds.init(event);
	}
}
