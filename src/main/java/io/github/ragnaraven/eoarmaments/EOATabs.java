package io.github.ragnaraven.eoarmaments;

import io.github.ragnaraven.eoarmaments.common.blocks.EOABlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Created by Ragnaraven on 1/9/2017 at 5:15 PM.
 */
public class EOATabs
{
	public static final CreativeModeTab TAB_EOAUNIVERSAL = new Tab_EOAUniversal();

	public static void initializeTabs ()
	{
	}

	public static class Tab_EOAUniversal extends CreativeModeTab {
		public Tab_EOAUniversal() {
			super("EOAUniversalTab");
		}

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(EOABlocks.ENDER_OBSIDIAN.get());
		}
	}
}
