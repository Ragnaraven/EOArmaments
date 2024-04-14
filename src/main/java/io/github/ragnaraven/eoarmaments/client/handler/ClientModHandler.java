package io.github.ragnaraven.eoarmaments.client.handler;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.client.KeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static org.openjdk.nashorn.internal.objects.Global.print;

@Mod.EventBusSubscriber(modid = EnderObsidianArmorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
           /* MenuScreens.register(MenuInit.EXAMPLE_MENU.get(), ExampleMenuScreen::new);
            MenuScreens.register(MenuInit.EXAMPLE_ENERGY_GENERATOR_MENU.get(), ExampleEnergyGeneratorScreen::new);
            MenuScreens.register(MenuInit.EXAMPLE_SIDED_INVENTORY_MENU.get(), ExampleSidedInventoryScreen::new);
            MenuScreens.register(MenuInit.EXAMPLE_FLUID_MENU.get(), ExampleFluidScreen::new);
            MenuScreens.register(MenuInit.EXAMPLE_BER_MENU.get(), ExampleBERScreen::new);
            MenuScreens.register(MenuInit.EXAMPLE_FLUID_BER_MENU.get(), ExampleFluidBERScreen::new);*/
        });
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.INSTANCE.abilityKey);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        /*// Entities
        event.registerEntityRenderer(EntityInit.EXAMPLE_ENTITY.get(), ExampleEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.EXAMPLE_ANIMATED_ENTITY.get(), ExampleAnimatedEntityRenderer::new);

        // Block Entities
        event.registerBlockEntityRenderer(BlockEntityInit.EXAMPLE_BER_BLOCK_ENTITY.get(), ExampleBER::new);
        event.registerBlockEntityRenderer(BlockEntityInit.EXAMPLE_FLUID_BER_BLOCK_ENTITY.get(), ExampleFluidBER::new);
        event.registerBlockEntityRenderer(BlockEntityInit.EXAMPLE_ANIMATED_BLOCK_ENTITY.get(), ExampleAnimatedBER::new);*/
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
       /* event.registerLayerDefinition(ExampleEntityModel.LAYER_LOCATION, ExampleEntityModel::createBodyLayer);
        event.registerLayerDefinition(ExampleAnimatedEntityModel.LAYER_LOCATION, ExampleAnimatedEntityModel::createBodyLayer);
        event.registerLayerDefinition(ExampleAnimatedBlockModel.LAYER_LOCATION, ExampleAnimatedBlockModel::createMainLayer);*/
    }
}
