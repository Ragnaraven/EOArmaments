package io.github.ragnaraven.eoarmaments.init;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.common.armor.*;
import io.github.ragnaraven.eoarmaments.common.items.tools.EnderObsidianSword;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.ragnaraven.eoarmaments.init.CreativeTabsInit.addToTab;

/**
 * Created by Ragnaraven on 1/9/2017 at 5:07 PM.
 */

@Mod.EventBusSubscriber
public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnderObsidianArmorsMod.MOD_ID);

//TODO: BALANCE SPEED AND DAMAGE
	public static final RegistryObject<Item> EMERALD_AXE    	= addToTab(ITEMS.register("emerald_axe", () -> new AxeItem(TierInit.EMERALD,	 	   	1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> EMERALD_HOE 		= addToTab(ITEMS.register("emerald_hoe", () -> new HoeItem(TierInit.EMERALD, 		   1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> EMERALD_PICKAXE 	= addToTab(ITEMS.register("emerald_pickaxe", () -> new PickaxeItem(TierInit.EMERALD, 	1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> EMERALD_SHOVEL		= addToTab(ITEMS.register("emerald_shovel", () -> new ShovelItem(TierInit.EMERALD,   	1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> EMERALD_SWORD 		= addToTab(ITEMS.register("emerald_sword", () -> new SwordItem(TierInit.EMERALD, 	   	1, 1.0f, new Item.Properties())));

	public static final RegistryObject<Item> OBSIDIAN_AXE    	= addToTab(ITEMS.register("obsidian_axe", () -> new AxeItem(TierInit.OBSIDIAN,	 	   1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> OBSIDIAN_HOE 		= addToTab(ITEMS.register("obsidian_hoe", () -> new HoeItem(TierInit.OBSIDIAN, 		   1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> OBSIDIAN_PICKAXE 	= addToTab(ITEMS.register("obsidian_pickaxe", () -> new PickaxeItem(TierInit.OBSIDIAN,   1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> OBSIDIAN_SHOVEL	= addToTab(ITEMS.register("obsidian_shovel", () -> new ShovelItem(TierInit.OBSIDIAN,     1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> OBSIDIAN_SWORD 	= addToTab(ITEMS.register("obsidian_sword", () -> new SwordItem(TierInit.OBSIDIAN, 	   1, 1.0f, new Item.Properties())));

	public static final RegistryObject<Item> ENDER_OBSIDIAN_AXE    		= addToTab(ITEMS.register("ender_obsidian_axe", () -> new AxeItem(TierInit.ENDER_OBSIDIAN,	 	   	 	1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_HOE 		= addToTab(ITEMS.register("ender_obsidian_hoe", () -> new HoeItem(TierInit.ENDER_OBSIDIAN, 		    	1,1.0f, new Item.Properties())));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_PICKAXE 	= addToTab(ITEMS.register("ender_obsidian_pickaxe", () -> new PickaxeItem(TierInit.ENDER_OBSIDIAN, 	  	1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_SHOVEL		= addToTab(ITEMS.register("ender_obsidian_shovel", () -> new ShovelItem(TierInit.ENDER_OBSIDIAN,   	 	1, 1.0f, new Item.Properties())));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_SWORD 		= addToTab(ITEMS.register("ender_obsidian_sword", () -> new EnderObsidianSword(TierInit.ENDER_OBSIDIAN,	1, 1.0f, new Item.Properties())));

	//Items
	public static final RegistryObject<Item> OBSIDIAN_PLATE       = addToTab(ITEMS.register("obsidian_plate", 	   () -> new Item(new Item.Properties())));
	public static final RegistryObject<Item> ENDER_OBSIDIAN_PLATE = addToTab(ITEMS.register("ender_obsidian_plate",  () -> new Item(new Item.Properties())));

	//Block items
	public static final RegistryObject<Item> ENDER_OBSIDIAN_BLOCK_ITEM = addToTab(ITEMS.register("block_ender_obsidian", () -> new BlockItem(BlockInit.ENDER_OBSIDIAN.get(), new Item.Properties())));

	//Armor
	public static final RegistryObject<Item> ARMOR_EMERALD_HEAD   = addToTab(ITEMS.register( "armor_emerald_head", 	() -> new ArmorItem(ArmorMaterialInit.EMERALD, ArmorItem.Type.HELMET,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_EMERALD_CHEST  = addToTab(ITEMS.register( "armor_emerald_chest", 	() -> new ArmorItem(ArmorMaterialInit.EMERALD, ArmorItem.Type.CHESTPLATE, new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_EMERALD_LEGS   = addToTab(ITEMS.register( "armor_emerald_legs", 	() -> new ArmorItem(ArmorMaterialInit.EMERALD, ArmorItem.Type.LEGGINGS,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_EMERALD_FEET   = addToTab(ITEMS.register( "armor_emerald_feet", 	() -> new ArmorItem(ArmorMaterialInit.EMERALD, ArmorItem.Type.BOOTS,  new Item.Properties())));

	public static final RegistryObject<Item> ARMOR_OBSIDIAN_HEAD   = addToTab(ITEMS.register( "armor_obsidian_head", 	() -> new ArmorItemObsidian(ArmorMaterialInit.OBSIDIAN, ArmorItem.Type.HELMET,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_CHEST  = addToTab(ITEMS.register( "armor_obsidian_chest", () -> new ArmorItemObsidian(ArmorMaterialInit.OBSIDIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_LEGS   = addToTab(ITEMS.register( "armor_obsidian_legs", 	() -> new ArmorItemObsidian(ArmorMaterialInit.OBSIDIAN, ArmorItem.Type.LEGGINGS,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_OBSIDIAN_FEET   = addToTab(ITEMS.register( "armor_obsidian_feet", 	() -> new ArmorItemObsidian(ArmorMaterialInit.OBSIDIAN, ArmorItem.Type.BOOTS,  new Item.Properties())));

	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_HEAD   = addToTab(ITEMS.register( "armor_ender_obsidian_head", 	() -> new ArmorItemEnderObsidian(ArmorMaterialInit.ENDER_OBSIDIAN, ArmorItem.Type.HELMET,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_CHEST  = addToTab(ITEMS.register( "armor_ender_obsidian_chest", () -> new ArmorItemEnderObsidian(ArmorMaterialInit.ENDER_OBSIDIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_LEGS   = addToTab(ITEMS.register( "armor_ender_obsidian_legs", 	() -> new ArmorItemEnderObsidian(ArmorMaterialInit.ENDER_OBSIDIAN, ArmorItem.Type.LEGGINGS,  new Item.Properties())));
	public static final RegistryObject<Item> ARMOR_ENDER_OBSIDIAN_FEET   = addToTab(ITEMS.register( "armor_ender_obsidian_feet", 	() -> new ArmorItemEnderObsidian(ArmorMaterialInit.ENDER_OBSIDIAN, ArmorItem.Type.BOOTS,  new Item.Properties())));


}
