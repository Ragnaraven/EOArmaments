package io.github.ragnaraven.eoarmaments.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.ragnaraven.eoarmaments.core.essentials.Experience;
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
public class CommandAddLevel
{
	@SubscribeEvent
	public static void registerEvent (RegisterCommandsEvent event)
	{
		LiteralArgumentBuilder<CommandSourceStack> addlevel =
				Commands.literal("addlevel")
				.requires(cmd -> cmd.hasPermission(3))
				.then(Commands.argument("level", IntegerArgumentType.integer())
					.executes(cmd -> addLevel(
							cmd.getSource(),
							cmd.getSource().getPlayerOrException(),
							IntegerArgumentType.getInteger(cmd, "level")))
				);

		event.getDispatcher().register(addlevel);
	}
	
	private static int addLevel(CommandSourceStack cmd, Player player, int count)
	{
		if (count < 1) cmd.sendFailure(Component.translatable("Level count must be bigger than 0!"));
		else
		{
			if (!EAUtils.canEnhance(player.getMainHandItem().getItem()))
				cmd.sendFailure(Component.translatable("Hold a weapon or an armor in your mainhand!"));
			else
			{
				ItemStack item = player.getMainHandItem();
				CompoundTag nbt = NBTHelper.loadStackNBT(item);
				for (int i = 0; i < count; i++) {
					if (Experience.canLevelUp(nbt)) {
						Experience.setExperience(nbt, Experience.getExperience(nbt) + Experience.getNeededExpForNextLevel(nbt));
						Experience.setLevel(nbt, Experience.getLevel(nbt) + 1);
						Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) + 1);
					}
				}
				NBTHelper.saveStackNBT(item, nbt);
				player.setItemInHand(InteractionHand.MAIN_HAND, item);
			}
		}
		return count;
	}
}