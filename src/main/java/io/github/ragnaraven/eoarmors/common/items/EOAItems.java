package io.github.ragnaraven.eoarmors.common.items;

import io.github.ragnaraven.eoarmors.EOATabs;
import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmors.common.armor.EOAArmorSuit;
import io.github.ragnaraven.eoarmors.common.armor.EOAArmorMaterial;
import io.github.ragnaraven.eoarmors.common.armor.EOAArmorSuitEnderObsidian;
import io.github.ragnaraven.eoarmors.common.armor.EOAArmorSuitObsidian;
import io.github.ragnaraven.eoarmors.common.items.tools.EnderObsidianSword;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Created by Ragnaraven on 1/9/2017 at 5:07 PM.
 */

@Mod.EventBusSubscriber
public class EOAItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderObsidianArmorsMod.MODID);

	public enum ModItemTier implements IItemTier {
		EMERALD(	    3, 5268, 9.5f,  5.50f, 12, () -> { return Ingredient.of(Items.EMERALD); } ),
		OBSIDIAN(		3, 200,  12.0f, 3.50f, 10, () -> { return Ingredient.of(Items.OBSIDIAN); } ),
		ENDER_OBSIDIAN( 3, 375,  14.5f, 4.25f, 15, () -> { return Ingredient.of(EOAItems.ENDER_OBSIDIAN_PLATE.get()); } );

		private final int level;
		private final int uses;
		private final float speed;
		private final float damage;
		private final int enchantmentValue;
		private final LazyValue<Ingredient> repairIngredient;

		private ModItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
			this.level = p_i48458_3_;
			this.uses = p_i48458_4_;
			this.speed = p_i48458_5_;
			this.damage = p_i48458_6_;
			this.enchantmentValue = p_i48458_7_;
			this.repairIngredient = new LazyValue<>(p_i48458_8_);
		}

		public int getUses() {
			return this.uses;
		}

		public float getSpeed() {
			return this.speed;
		}

		public float getAttackDamageBonus() {
			return this.damage;
		}

		public int getLevel() {
			return this.level;
		}

		public int getEnchantmentValue() {
			return this.enchantmentValue;
		}

		public Ingredient getRepairIngredient() {
			return this.repairIngredient.get();
		}
	}

//TODO: BALANCE SPEED AND DAMAGE
	public static final RegistryObject<Item> EMERALD_AXE    	= ITEMS.register("emerald_axe", () -> new AxeItem(ModItemTier.EMERALD,	 	   	1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> EMERALD_HOE 		= ITEMS.register("emerald_hoe", () -> new HoeItem(ModItemTier.EMERALD, 		   1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> EMERALD_PICKAXE 	= ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(ModItemTier.EMERALD, 	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> EMERALD_SHOVEL		= ITEMS.register("emerald_shovel", () -> new ShovelItem(ModItemTier.EMERALD,   	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> EMERALD_SWORD 		= ITEMS.register("emerald_sword", () -> new SwordItem(ModItemTier.EMERALD, 	   	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));

	public static final RegistryObject<Item> OBSIDIAN_AXE    	= ITEMS.register("obsidian_axe", () -> new AxeItem(ModItemTier.OBSIDIAN,	 	   1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> OBSIDIAN_HOE 		= ITEMS.register("obsidian_hoe", () -> new HoeItem(ModItemTier.OBSIDIAN, 		   1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> OBSIDIAN_PICKAXE 	= ITEMS.register("obsidian_pickaxe", () -> new PickaxeItem(ModItemTier.OBSIDIAN,   1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> OBSIDIAN_SHOVEL	= ITEMS.register("obsidian_shovel", () -> new ShovelItem(ModItemTier.OBSIDIAN,     1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> OBSIDIAN_SWORD 	= ITEMS.register("obsidian_sword", () -> new SwordItem(ModItemTier.OBSIDIAN, 	   1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));

	public static final RegistryObject<Item> ENDER_OBSIDIAN_AXE    		= ITEMS.register("ender_obsidian_axe", () -> new AxeItem(ModItemTier.ENDER_OBSIDIAN,	 	   	 	1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_HOE 		= ITEMS.register("ender_obsidian_hoe", () -> new HoeItem(ModItemTier.ENDER_OBSIDIAN, 		    	1,1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_PICKAXE 	= ITEMS.register("ender_obsidian_pickaxe", () -> new PickaxeItem(ModItemTier.ENDER_OBSIDIAN, 	  	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_SHOVEL		= ITEMS.register("ender_obsidian_shovel", () -> new ShovelItem(ModItemTier.ENDER_OBSIDIAN,   	 	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_SWORD 		= ITEMS.register("ender_obsidian_sword", () -> new EnderObsidianSword(ModItemTier.ENDER_OBSIDIAN,	1, 1.0f, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));

	//Items
	public static final RegistryObject<Item> OBSIDIAN_PLATE       = ITEMS.register("obsidian_plate", 	   () -> new Item(new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_PLATE = ITEMS.register("ender_obsidian_plate", () -> new Item(new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));

	//Block items
	public static final RegistryObject<Item> ENDER_OBSIDIAN_BLOCK_ITEM = ITEMS.register("block_ender_obsidian", () -> new BlockItem(EOABlocks.ENDER_OBSIDIAN.get(), new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL)));

	//Armor
	public static final EOAArmorSuit ARMOR_EMERALD = new EOAArmorSuit("emerald", EOAArmorMaterial.EMERALD);
	public static final RegistryObject<Item> ARMOR_EMERALD_HEAD   = ITEMS.register(ARMOR_EMERALD.name_head(), 	() -> { return ARMOR_EMERALD.head().getItem(); });
	public static final RegistryObject<Item> ARMOR_EMERALD_CHEST  = ITEMS.register(ARMOR_EMERALD.name_chest(), 	() -> { return ARMOR_EMERALD.chest().getItem(); });
	public static final RegistryObject<Item> ARMOR_EMERALD_LEGS   = ITEMS.register(ARMOR_EMERALD.name_legs(), 	() -> { return ARMOR_EMERALD.legs().getItem(); });
	public static final RegistryObject<Item> ARMOR_EMERALD_FEET   = ITEMS.register(ARMOR_EMERALD.name_feet(), 	() -> { return ARMOR_EMERALD.feet().getItem(); });

	public static final EOAArmorSuit ARMOR_OBSIDIAN = new EOAArmorSuitObsidian("obsidian", EOAArmorMaterial.OBSIDIAN);
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_HEAD   = ITEMS.register(ARMOR_OBSIDIAN.name_head(),   () -> { return ARMOR_OBSIDIAN.head().getItem(); });
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_CHEST  = ITEMS.register(ARMOR_OBSIDIAN.name_chest(),  () -> { return ARMOR_OBSIDIAN.chest().getItem(); });
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_LEGS   = ITEMS.register(ARMOR_OBSIDIAN.name_legs(),   () -> { return ARMOR_OBSIDIAN.legs().getItem(); });
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_FEET   = ITEMS.register(ARMOR_OBSIDIAN.name_feet(),   () -> { return ARMOR_OBSIDIAN.feet().getItem(); });

	public static final EOAArmorSuit ARMOR_ENDER_OBSIDIAN = new EOAArmorSuitEnderObsidian("ender_obsidian", EOAArmorMaterial.ENDER_OBSIDIAN);
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_HEAD   = ITEMS.register(ARMOR_ENDER_OBSIDIAN.name_head(), 	() -> { return ARMOR_ENDER_OBSIDIAN.head().getItem(); });
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_CHEST  = ITEMS.register(ARMOR_ENDER_OBSIDIAN.name_chest(),	() -> { return ARMOR_ENDER_OBSIDIAN.chest().getItem(); });
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_LEGS   = ITEMS.register(ARMOR_ENDER_OBSIDIAN.name_legs(), 	() -> { return ARMOR_ENDER_OBSIDIAN.legs().getItem(); });
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_FEET   = ITEMS.register(ARMOR_ENDER_OBSIDIAN.name_feet(), 	() -> { return ARMOR_ENDER_OBSIDIAN.feet().getItem(); });

}
