package io.github.ragnaraven.eoarmors.core.util;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class NBTHelper 
{
	public static CompoundTag loadStackNBT(ItemStack stack)
	{
		return stack.hasTag() ? stack.getTag() : new CompoundTag();
	}

	public static void saveStackNBT(ItemStack stack, CompoundTag nbt)
	{
		//if (!stack.hasTag())
		{
			stack.setTag(nbt);
		}
	}
}