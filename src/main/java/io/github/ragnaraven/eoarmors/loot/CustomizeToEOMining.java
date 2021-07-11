package io.github.ragnaraven.eoarmors.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.ragnaraven.eoarmors.core.eventlisteners.EOABlockBreakEventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class CustomizeToEOMining extends LootFunction {

    public CustomizeToEOMining(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context)
    {
        if (!stack.isEmpty() && context.getParamOrNull(LootParameters.THIS_ENTITY) instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) context.getParamOrNull(LootParameters.THIS_ENTITY);
            if (playerEntity == null)
                return stack;

            BlockState blockState = (BlockState) context.getParamOrNull(LootParameters.BLOCK_STATE);
            if (blockState == null)
                return stack;

            Block block = blockState.getBlock();

            return EOABlockBreakEventHandler.ON_HARVEST_DROPS(playerEntity, block, stack);
        }

        return stack;
    }

    @Override
    public LootFunctionType getType()
    {
        return EOALootRegistry.CUSTOMIZE_TO_EO_MINING;
    }

    public static class Serializer extends LootFunction.Serializer<CustomizeToEOMining> {
        public Serializer() {
            super();
        }

        public void serialize(JsonObject object, CustomizeToEOMining functionClazz, JsonSerializationContext serializationContext) {

        }

        public CustomizeToEOMining deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
            return new CustomizeToEOMining(conditionsIn);
        }
    }
}