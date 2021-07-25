package io.github.ragnaraven.eoarmors.common.commands;

import io.github.ragnaraven.eoarmors.core.essentials.Experience;
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
public class CommandAddLevel
{
	@SubscribeEvent
	public static void registerEvent (RegisterCommandsEvent event)
	{
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

		dispatcher.register(Commands.literal("addlevel")
				.requires(cmd -> cmd.hasPermission(3))
				.then(Commands.argument("level", IntegerArgumentType.integer()))
				.executes(cmd -> addLevel(cmd.getSource(), cmd.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(cmd, "level"))));
	}
	
	private static int addLevel(CommandSource cmd, PlayerEntity player, int count)
	{
		if (count < 1) cmd.sendFailure(new TranslationTextComponent("Level count must be bigger than 0!"));
		else
		{
			if (!EAUtils.canEnhance(player.getMainHandItem().getItem()))
				cmd.sendFailure(new TranslationTextComponent("Hold a weapon or an armor in your mainhand!"));
			else
			{
				ItemStack item = player.getMainHandItem();
				CompoundNBT nbt = NBTHelper.loadStackNBT(item);
				for (int i = 0; i < count; i++) {
					if (Experience.canLevelUp(nbt)) {
						Experience.setExperience(nbt, Experience.getExperience(nbt) + Experience.getNeededExpForNextLevel(nbt));
						Experience.setLevel(nbt, Experience.getLevel(nbt) + 1);
						Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) + 1);
					}
				}
				NBTHelper.saveStackNBT(item, nbt);
				player.setItemInHand(Hand.MAIN_HAND, item);
			}
		}
		return count;
	}
}