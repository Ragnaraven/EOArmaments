package io.github.ragnaraven.eoarmaments.init;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagInit {
    public static final TagKey<Block> NEEDS_EXAMPLE_TOOL = tag("needs_example_tool");

    private static TagKey<Block> tag(String name) {
        return BlockTags.create(new ResourceLocation(EnderObsidianArmorsMod.MOD_ID, name));
    }
}
