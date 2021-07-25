package io.github.ragnaraven.eoarmors.core.eventlisteners;

import io.github.ragnaraven.eoarmors.client.render.gui.GuiAbilitySelection;
import io.github.ragnaraven.eoarmors.init.EOAKeyBinds;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Opens the weapon ability selection gui on key press.
 *
 */
@Mod.EventBusSubscriber
public class EventInput
{
	@SubscribeEvent
	public void onKeyPress(InputUpdateEvent event)
	{
		KeyBinding key = EOAKeyBinds.abilityKey;
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		
		if (player != null)
		{
			ItemStack stack = player.getMainHandItem();
			
			if (stack != ItemStack.EMPTY)
			{
				if (EAUtils.canEnhance(stack.getItem()))
				{
					if (key.isDown() && stack.hasTag())
						if(stack.getTag().contains("EA_ENABLED"))
							mc.setScreen(new GuiAbilitySelection(new StringTextComponent(I18n.get("eoarmors.mainscreen.title"))));
				}
			}
		}
	}
}