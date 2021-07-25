package io.github.ragnaraven.eoarmors.common.commands;

import java.util.List;

import com.google.common.collect.Lists;
import io.github.ragnaraven.eoarmors.core.essentials.Rarity;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
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
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

		dispatcher.register(Commands.literal("changerarity")
				.requires(cmd -> cmd.hasPermission(3))
				.then(Commands.argument("rarityid", IntegerArgumentType.integer()))
				.executes(cmd -> changeRarity(cmd.getSource(), cmd.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(cmd, "rarityid"))));
	}
	
	public static int changeRarity(CommandSource src, PlayerEntity player, int rarityid)
	{
		if((rarityid < 1) || (rarityid > 6))
			src.sendFailure(new TranslationTextComponent("Rarity ID must be 1, 2, 3, 4, 5 or 6!"));
		else
		{
			if (!EAUtils.canEnhance(player.getMainHandItem().getItem()))
				src.sendFailure(new TranslationTextComponent("Hold a weapon or an armor in your mainhand!"));
			else
			{
				ItemStack item = player.getMainHandItem();
				CompoundNBT nbt = NBTHelper.loadStackNBT(item);
				Rarity.setRarity(nbt, String.valueOf(rarityid));
				NBTHelper.saveStackNBT(item, nbt);
				player.setItemInHand(Hand.MAIN_HAND, item);
			}
		}
		return rarityid;
	}
}