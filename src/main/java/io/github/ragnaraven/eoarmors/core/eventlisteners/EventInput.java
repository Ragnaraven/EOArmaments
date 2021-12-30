package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.client.render.gui.GuiAbilitySelection;
import io.github.ragnaraven.eoarmors.init.EOAKeyBinds;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Opens the weapon ability selection gui on key press.
 *
 */
@Mod.EventBusSubscriber
public class EventInput
{
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void onKeyPress(InputEvent event)
	{
		KeyMapping key = EOAKeyBinds.abilityKey;
		Minecraft mc = Minecraft.getInstance();
		Player player = mc.player;
		
		if (player != null)
		{
			ItemStack stack = player.getMainHandItem();
			
			if (stack != ItemStack.EMPTY)
			{
				if (EAUtils.canEnhance(stack.getItem()))
				{
					if (key.isDown() && stack.hasTag())
						if(stack.getTag().contains("EA_ENABLED"))
							mc.setScreen(new GuiAbilitySelection(new TextComponent(I18n.get("eoarmors.mainscreen.title"))));
				}
			}
		}
	}
}