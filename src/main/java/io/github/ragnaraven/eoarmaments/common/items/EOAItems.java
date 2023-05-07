package io.github.ragnaraven.eoarmaments.common.items;

import io.github.ragnaraven.eoarmaments.EOATabs;
import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.common.armor.*;
import io.github.ragnaraven.eoarmaments.common.blocks.EOABlocks;
import io.github.ragnaraven.eoarmaments.common.items.tools.EnderObsidianSword;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Created by Ragnaraven on 1/9/2017 at 5:07 PM.
 */

@Mod.EventBusSubscriber
public class EOAItems
{

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderObsidianArmorsMod.MODID);

	public enum ModItemTier implements Tier {
		EMERALD(	    3, 5268, 9.5f,  5.50f, 12, () -> { return Ingredient.of(Items.EMERALD); } ),
		OBSIDIAN(		3, 500,  12.0f, 3.50f, 10, () -> { return Ingredient.of(Items.OBSIDIAN); } ),
		ENDER_OBSIDIAN( 3, 2500,  16f, 4.25f, 15, () -> { return Ingredient.of(EOAItems.ENDER_OBSIDIAN_PLATE.get()); } );

		private final int level;
		private final int uses;
		private final float speed;
		private final float damage;
		private final int enchantmentValue;
		private final Supplier<Ingredient> repairIngredient;

		private ModItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
			this.level = p_i48458_3_;
			this.uses = p_i48458_4_;
			this.speed = p_i48458_5_;
			this.damage = p_i48458_6_;
			this.enchantmentValue = p_i48458_7_;
			this.repairIngredient = p_i48458_8_;
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
	public static final RegistryObject<Item> ARMOR_EMERALD_HEAD   = ITEMS.register( "armor_emerald_head", 	() -> new ModArmorItem("emerald", EOAArmorMaterial.EMERALD, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_EMERALD_CHEST  = ITEMS.register( "armor_emerald_chest", 	() -> new ModArmorItem("emerald", EOAArmorMaterial.EMERALD, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_EMERALD_LEGS   = ITEMS.register( "armor_emerald_legs", 	() -> new ModArmorItem("emerald", EOAArmorMaterial.EMERALD, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_EMERALD_FEET   = ITEMS.register( "armor_emerald_feet", 	() -> new ModArmorItem("emerald", EOAArmorMaterial.EMERALD, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));

	public static final RegistryObject<Item> ARMOR_OBSIDIAN_HEAD   = ITEMS.register( "armor_obsidian_head", 	() -> new ArmorItemObsidian("obsidian", EOAArmorMaterial.OBSIDIAN, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_CHEST  = ITEMS.register( "armor_obsidian_chest", 	() -> new ArmorItemObsidian("obsidian", EOAArmorMaterial.OBSIDIAN, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_LEGS   = ITEMS.register( "armor_obsidian_legs", 	() -> new ArmorItemObsidian("obsidian", EOAArmorMaterial.OBSIDIAN, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_FEET   = ITEMS.register( "armor_obsidian_feet", 	() -> new ArmorItemObsidian("obsidian", EOAArmorMaterial.OBSIDIAN, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));

	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_HEAD   = ITEMS.register( "armor_ender_obsidian_head", 	() -> new ArmorItemEnderObsidian("ender_obsidian", EOAArmorMaterial.ENDER_OBSIDIAN, EquipmentSlot.HEAD,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_CHEST  = ITEMS.register( "armor_ender_obsidian_chest", 	() -> new ArmorItemEnderObsidian("ender_obsidian", EOAArmorMaterial.ENDER_OBSIDIAN, EquipmentSlot.CHEST, new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_LEGS   = ITEMS.register( "armor_ender_obsidian_legs", 	() -> new ArmorItemEnderObsidian("ender_obsidian", EOAArmorMaterial.ENDER_OBSIDIAN, EquipmentSlot.LEGS,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_FEET   = ITEMS.register( "armor_ender_obsidian_feet", 	() -> new ArmorItemEnderObsidian("ender_obsidian", EOAArmorMaterial.ENDER_OBSIDIAN, EquipmentSlot.FEET,  new Item.Properties().tab(EOATabs.TAB_EOAUNIVERSAL) ));


}
