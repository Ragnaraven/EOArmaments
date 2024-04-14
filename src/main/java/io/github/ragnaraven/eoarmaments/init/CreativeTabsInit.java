package io.github.ragnaraven.eoarmaments.init;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Ragnaraven on 1/9/2017 at 5:15 PM.
 */
public class CreativeTabsInit
{
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EnderObsidianArmorsMod.MOD_ID);

	public static final List<Supplier<? extends ItemLike>> EOA_TAB_ITEMS = new ArrayList<>();

	public static final RegistryObject<CreativeModeTab> EOA_TAB = TABS.register("eoa_universal_tab",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.eoa_universal_tab"))
					.icon(ItemInit.ENDER_OBSIDIAN_BLOCK_ITEM.get()::getDefaultInstance)
					.displayItems((displayParams, output) -> EOA_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
					.withSearchBar()
					.build()
	);

	public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
		EOA_TAB_ITEMS.add(itemLike);
		return itemLike;
	}

	@SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.getEntries().putAfter(
					Items.ACACIA_LOG.getDefaultInstance(),
					ItemInit.ENDER_OBSIDIAN_BLOCK_ITEM.get().getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}

		if(event.getTab() == EOA_TAB.get()) {
			event.accept(Items.CROSSBOW);
		}
	}
}
