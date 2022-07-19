package io.github.ragnaraven.eoarmaments.loot;

import com.google.gson.JsonObject;
import io.github.ragnaraven.eoarmaments.core.eventlisteners.EOABlockBreakEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;

public class CustomizeToEOMining extends LootModifier {

    public CustomizeToEOMining(LootItemCondition[] conditionsIn) {
        super(conditionsIn == null ? new LootItemCondition[]{} : conditionsIn);
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
    {
        try {
            Player playerEntity = (Player) context.getParamOrNull(LootContextParams.THIS_ENTITY);
            BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

            if(playerEntity != null && blockState != null)
            {
                Block block = blockState.getBlock();

                return EOABlockBreakEventHandler.ON_HARVEST_DROPS(playerEntity, block, generatedLoot);
            }
        }
        catch (Exception e) {}

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<CustomizeToEOMining> {

        public Serializer() {
            super();
        }

        @Override
        public CustomizeToEOMining read(ResourceLocation location, JsonObject object, LootItemCondition[] conditionsIn) {
            return new CustomizeToEOMining(conditionsIn == null ? new LootItemCondition[]{} : conditionsIn);
        }

        @Override
        public JsonObject write(CustomizeToEOMining instance) {
            return null;
        }
    }
}