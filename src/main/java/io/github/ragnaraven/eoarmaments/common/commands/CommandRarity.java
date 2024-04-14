package io.github.ragnaraven.eoarmaments.common.commands;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.ragnaraven.eoarmaments.core.essentials.Rarity;
import io.github.ragnaraven.eoarmaments.core.util.EAUtils;
import io.github.ragnaraven.eoarmaments.core.util.NBTHelper;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandRarity
{
	private final List<String> aliases = Lists.newArrayList("changerarity");

	@SubscribeEvent
	public static void registerEvent (RegisterCommandsEvent event)
	{
		LiteralArgumentBuilder<CommandSourceStack> changerarity = Commands.literal("changerarity")
				.requires(cmd -> cmd.hasPermission(3))
				.then(Commands.argument("rarityid", IntegerArgumentType.integer())
					.executes(cmd -> changeRarity(cmd.getSource(), cmd.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(cmd, "rarityid")))
				);

		event.getDispatcher().register(changerarity);
	}
	
	public static int changeRarity(CommandSourceStack src, Player player, int rarityid)
	{
		if((rarityid < 1) || (rarityid > 6))
			src.sendFailure(Component.translatable("Rarity ID must be 1, 2, 3, 4, 5 or 6!"));
		else
		{
			if (!EAUtils.canEnhance(player.getMainHandItem().getItem()))
				src.sendFailure(Component.translatable("Hold a weapon or an armor in your mainhand!"));
			else
			{
				ItemStack item = player.getMainHandItem();
				CompoundTag nbt = NBTHelper.loadStackNBT(item);
				Rarity.setRarity(nbt, String.valueOf(rarityid));
				NBTHelper.saveStackNBT(item, nbt);
				player.setItemInHand(InteractionHand.MAIN_HAND, item);
			}
		}
		return rarityid;
	}
}