package io.github.ragnaraven.eoarmaments;

import io.github.ragnaraven.eoarmaments.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmaments.common.items.EOAItems;
import io.github.ragnaraven.eoarmaments.config.ConfigHolder;
import io.github.ragnaraven.eoarmaments.init.EOAKeyBinds;
import io.github.ragnaraven.eoarmaments.network.PacketGuiAbility;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(EnderObsidianArmorsMod.MODID)
public class EnderObsidianArmorsMod
{
    public static final String MODID = "eoarmaments";
	
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

		//EOALootRegistry.init();

		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);

		//MinecraftForge.EVENT_BUS.register(EAOGeneral.class);
	}

	private void setup(FMLCommonSetupEvent event)
	{
		network.registerMessage(0, PacketGuiAbility.class, PacketGuiAbility::encode, PacketGuiAbility::decode, PacketGuiAbility::handle);
	}

	private void clientInit(FMLClientSetupEvent event)
	{
		EOAKeyBinds.init(event);
	}
}
