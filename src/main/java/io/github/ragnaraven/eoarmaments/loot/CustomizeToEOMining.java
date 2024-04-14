package io.github.ragnaraven.eoarmaments.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.ragnaraven.eoarmaments.core.eventlisteners.EOABlockBreakEventHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class CustomizeToEOMining extends LootModifier {
    public static final Codec<CustomizeToEOMining> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).apply(instance, CustomizeToEOMining::new)
    );

    public CustomizeToEOMining(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        try {
            if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player playerEntity) {
                BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

                if (blockState != null) {
                    Block block = blockState.getBlock();

                    return EOABlockBreakEventHandler.ON_HARVEST_DROPS(playerEntity, block, generatedLoot);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}