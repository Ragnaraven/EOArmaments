package io.github.ragnaraven.eoarmors.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class NBTHelper 
{
	public static CompoundNBT loadStackNBT(ItemStack stack)
	{
		return stack.hasTag() ? stack.getTag() : new CompoundNBT();
	}

	public static void saveStackNBT(ItemStack stack, CompoundNBT nbt)
	{
		if (!stack.hasTag())
		{
			stack.setTag(nbt);
		}
	}
}