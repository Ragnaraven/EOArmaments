package io.github.ragnaraven.eoarmaments.config;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmaments.core.eventlisteners.EOAEnderObsidianEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ServerConfig {

    //Mining
    public final ForgeConfigSpec.IntValue LAVA_SPAWN_CHANCE;
    public final ForgeConfigSpec.IntValue EXP_CHANCE;
    public final ForgeConfigSpec.IntValue BASE_EXP;
    public final ForgeConfigSpec.IntValue EXP_RANDOM;
    public final ForgeConfigSpec.IntValue OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE;
    public final ForgeConfigSpec.IntValue OBSIDIAN_TO_MINE_FULL_HEALTH_INVENTORY;
    public final ForgeConfigSpec.IntValue HEAL_ARMOR_CHANCE;
    public final ForgeConfigSpec.IntValue HEAL_ARMOR_DURABILITY;
    public final ForgeConfigSpec.IntValue HEAL_ARMOR_EXP_DROP_AMOUNT;
    public final ForgeConfigSpec.IntValue CHANCE_TO_SILK_TOUCH_OBSIDIAN;
    public final ForgeConfigSpec.IntValue CHANCE_TO_CONVERT_OBSIDIAN_TO_ENDER_OBSIDIAN;

    //EO
    public final ForgeConfigSpec.IntValue CHANCE_TO_SPAWN_ENDER_OBSIDIAN_MODS;
    public final ForgeConfigSpec.IntValue CHANCE_TO_SPAWN_ENDER_OBSIDIAN_STANDARD;

    public ServerConfig(final ForgeConfigSpec.Builder builder)
    {
        builder.push("Obsidian Mining With (Ender) Obsidian Suit and Pick");
        this.LAVA_SPAWN_CHANCE = buildInt(builder, "Lava Spawn Chance", "all", 11, 0, 10000, "Chance for lava to spawn when mining obsidian with a full suit of either kind of obsidian. Chance is 1 in [value]");
        this.EXP_CHANCE = buildInt(builder, "Experience Chance", "all", 1, 0, 10000, "Base experience for obsidian to drop. Literal value.");
        this.BASE_EXP = buildInt(builder, "Base Exp", "all", 10, 0, 10000, "Base experience for obsidian to drop. Literal value.");
        this.EXP_RANDOM = buildInt(builder, "Base Exp Deviation", "all", 64, 0, 10000, "Additonal random xp. Literal value.");

        this.OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE = buildInt(builder, "Obsidian to mine to full Pick Health", "all", 4, 0, 10000, "Number of obsidian to mine to heal a pick to full health. Literal value.");
        this.OBSIDIAN_TO_MINE_FULL_HEALTH_INVENTORY = buildInt(builder, "Obsidian to mine to full inventory health", "all", 4, 0, 10000, "Number of obsidian to mine to heal a non-armor tools that are not picks to full health while mining obsidian. Literal value added to obsidianToMineUntilFullPickHealth.");

        this.HEAL_ARMOR_CHANCE = buildInt(builder, "Armor Heal Chance on mine", "all", 1, 0, 10000, "Chance to heal armor when mining obsidian. 1 in AMOUNT chance.");
        this.HEAL_ARMOR_DURABILITY = buildInt(builder, "Armor Heal Amount", "all", 8, 0, 10000, "How much durability to heal armor when mining obsidian.");
        this.HEAL_ARMOR_EXP_DROP_AMOUNT = buildInt(builder, "Armor Heal Exp Drop Chance (always 1xp)", "all", 1, 0, 10000, "Chance to drop 1 xp when mining obsidian and armor is healed.");
        this.CHANCE_TO_SILK_TOUCH_OBSIDIAN = buildInt(builder, "Chance to Silk Touch Obsidian and Drop instead of Consuming", "all", 7, 0, 1000, "Chance to drop obsidian when wearing an O or EO armor set and using a matching pick with silk touch.");
        this.CHANCE_TO_CONVERT_OBSIDIAN_TO_ENDER_OBSIDIAN = buildInt(builder, "Chance to Convert Obsidian to Ender Obsidian while mining in End", "all", 7, 0, 1000, "Chance to drop ender obsidian while in the end with an EO armor set and using a matching pick with silk touch.");

        builder.pop();
        builder.push("Ender Obsidian Spawning");
        this.CHANCE_TO_SPAWN_ENDER_OBSIDIAN_MODS = buildInt(builder, "Chance to Silk Touch Obsidian and Drop instead of Consuming", "all",      EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.defaultVal, EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.min,     EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.max, "Chance to spawn ender obsidian with game changing mods detected.");
        this.CHANCE_TO_SPAWN_ENDER_OBSIDIAN_STANDARD = buildInt(builder, "Chance to Convert Obsidian to Ender Obsidian while mining in End", "all", EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.defaultVal,     EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.min,         EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.max,     "Chance to spawn ender obsidian without game changing mods detected.");

        builder.pop();

        buildMain(builder);
        buildMisc(builder);
        buildAbilities(builder);
        buildAbilityChance(builder);
        buildRarities(builder);
        buildMultiplier(builder);
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, String catagory, int defaultValue, int min, int max, String comment){
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    ///////////////////////////////////////
    //FROM EA
    ///////////////////////////////////////
    private static List<Item> parseItemList(List<String> lst)
    {
        List<Item> exp = new ArrayList<>(lst.size());
        for (String s : lst) {
            Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
            if (i == null || i == Items.AIR) {
                EnderObsidianArmorsMod.LOGGER.error("Invalid config entry {} will be ignored from blacklist.", s);
                continue;
            }
            exp.add(i);
        }
        return exp;
    }

    public ForgeConfigSpec.ConfigValue<Integer> maxLevel;
    public ForgeConfigSpec.ConfigValue<Integer> level1Experience;
    public ForgeConfigSpec.ConfigValue<Double> experienceMultiplier;

    public ForgeConfigSpec.BooleanValue showDurabilityInTooltip;
    public ForgeConfigSpec.ConfigValue<List<String>> itemBlacklist;
    public ForgeConfigSpec.ConfigValue<List<String>> itemWhitelist;
    public ForgeConfigSpec.ConfigValue<List<String>> extraItems;
    public ForgeConfigSpec.BooleanValue onlyModdedItems;

    public ForgeConfigSpec.BooleanValue fireAbility;
    public ForgeConfigSpec.BooleanValue frostAbility;
    public ForgeConfigSpec.BooleanValue poisonAbility;
    public ForgeConfigSpec.BooleanValue innateAbility;
    public ForgeConfigSpec.BooleanValue bombasticAbility;
    public ForgeConfigSpec.BooleanValue criticalpointAbility;
    public ForgeConfigSpec.BooleanValue illuminationAbility;
    public ForgeConfigSpec.BooleanValue etherealAbility;
    public ForgeConfigSpec.BooleanValue bloodthirstAbility;

    public ForgeConfigSpec.BooleanValue moltenAbility;
    public ForgeConfigSpec.BooleanValue frozenAbility;
    public ForgeConfigSpec.BooleanValue toxicAbility;
    public ForgeConfigSpec.BooleanValue adrenalineAbility;
    public ForgeConfigSpec.BooleanValue beastialAbility;
    public ForgeConfigSpec.BooleanValue remedialAbility;
    public ForgeConfigSpec.BooleanValue hardenedAbility;

    public ForgeConfigSpec.ConfigValue<Double> firechance;
    public ForgeConfigSpec.ConfigValue<Double> frostchance;
    public ForgeConfigSpec.ConfigValue<Double> poisonchance;
    public ForgeConfigSpec.ConfigValue<Double> innatechance;
    public ForgeConfigSpec.ConfigValue<Double> bombasticchance;
    public ForgeConfigSpec.ConfigValue<Double> criticalpointchance;
    public ForgeConfigSpec.ConfigValue<Double> moltenchance;
    public ForgeConfigSpec.ConfigValue<Double> frozenchance;
    public ForgeConfigSpec.ConfigValue<Double> toxicchance;
    public ForgeConfigSpec.ConfigValue<Double> adrenalinechance;
    public ForgeConfigSpec.ConfigValue<Double> hardenedchance;

    public ForgeConfigSpec.ConfigValue<Double> basicChance;
    public ForgeConfigSpec.ConfigValue<Double> uncommonChance;
    public ForgeConfigSpec.ConfigValue<Double> rareChance;
    public ForgeConfigSpec.ConfigValue<Double> ultraRareChance;
    public ForgeConfigSpec.ConfigValue<Double> legendaryChance;
    public ForgeConfigSpec.ConfigValue<Double> archaicChance;

    public ForgeConfigSpec.ConfigValue<Double> basicDamage;
    public ForgeConfigSpec.ConfigValue<Double> uncommonDamage;
    public ForgeConfigSpec.ConfigValue<Double> rareDamage;
    public ForgeConfigSpec.ConfigValue<Double> ultraRareDamage;
    public ForgeConfigSpec.ConfigValue<Double> legendaryDamage;
    public ForgeConfigSpec.ConfigValue<Double> archaicDamage;

    private void buildMain(ForgeConfigSpec.Builder builder)
    {
        builder.push("experience");

        maxLevel = builder
                .comment("Sets the maximum level cap for weapons and armor. Default: 10")
                .define("maxLevel", 10);

        level1Experience = builder
                .comment("The experience amount needed for the first level(1). Default: 100")
                .define("level1Experience", 100);

        experienceMultiplier = builder
                .comment("The experience multiplier for each level based on the first level experience. Default: 1.8")
                .define("experienceMultiplier", 1.8);

        builder.pop();
    }

    private void buildMisc(ForgeConfigSpec.Builder builder)
    {
        builder.push("miscellaneous");

        showDurabilityInTooltip = builder
                .comment("Determines whether or not durability will be displayed in tooltips. Default: true")
                .define("showDurabilityInTooltip", true);

        itemBlacklist = builder
                .comment("Items in this blacklist will not gain the leveling systems. Useful for very powerful items or potential conflicts. Style should be 'modid:item'")
                .define("itemBlacklist", new ArrayList<>());

        itemWhitelist = builder
                .comment("This is item whitelist, basically. If you don't want a whitelist, just leave this empty. If you want a whitelist, fill it with items you want. Style should be 'modid:item'")
                .define("itemWhitelist", new ArrayList<>());

        extraItems = builder
                .comment("This is an extra item list to add custom support for such modded items. Be careful on this, it may crash if the item can't be enhanced. Style should be 'modid:item'")
                .define("extraItems", new ArrayList<>());

        onlyModdedItems = builder
                .comment("Determines if the vanilla items won't be affected by this mod. Default: false")
                .define("onlyModdedItems", false);

        builder.pop();
    }

    private void buildAbilities(ForgeConfigSpec.Builder builder)
    {
        builder.push("abilities");

        // weapons
        fireAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("fireAbility", true);

        frostAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("frostAbility", true);

        poisonAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("poisonAbility", true);

        innateAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("innateAbility", true);

        bombasticAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("bombasticAbility", true);

        criticalpointAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("criticalpointAbility", true);

        illuminationAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("illuminationAbility", true);

        etherealAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("etherealAbility", true);

        bloodthirstAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("bloodthirstAbility", true);

        //armor
        moltenAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("moltenAbility", true);

        frozenAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("frozenAbility", true);

        moltenAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("moltenAbility", true);

        toxicAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("toxicAbility", true);

        adrenalineAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("adrenalineAbility", true);

        beastialAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("beastialAbility", true);

        remedialAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("remedialAbility", true);

        hardenedAbility = builder
                .comment("Determines whether or not the specific ability will be present in-game. Default: true")
                .define("hardenedAbility", true);

        builder.pop();
    }

    private void buildAbilityChance(ForgeConfigSpec.Builder builder)
    {
        builder.push("abilitychances");

        firechance = builder
                .comment("Determines how rare the Fire ability will occur. (Higher values=lower occurance) Default: 4")
                .define("firechance", 4D);

        frostchance = builder
                .comment("Determines how rare the Frost ability will occur. (Higher values=lower occurance) Default: 4")
                .define("frostchance", 4d);

        poisonchance = builder
                .comment("Determines how rare the Poison ability will occur. (Higher values=lower occurance) Default: 4")
                .define("poisonchance", 4d);

        innatechance = builder
                .comment("Determines how rare the Innate ability will occur. (Higher values=lower occurance) Default: 4")
                .define("innatechance", 4d);

        bombasticchance = builder
                .comment("Determines how rare the Bombasitc ability will occur. (Higher values=lower occurance) Default: 4")
                .define("bombasticchance", 4d);

        criticalpointchance = builder
                .comment("Determines how rare the Critical Point ability will occur. (Higher values=lower occurance) Default: 4")
                .define("criticalpointchance", 4d);

        moltenchance = builder
                .comment("Determines how rare the Molten ability will occur. (Higher values=lower occurance) Default: 4")
                .define("moltenchance", 4d);

        frozenchance = builder
                .comment("Determines how rare the Frozen ability will occur. (Higher values=lower occurance) Default: 4")
                .define("frozenchance", 4d);

        toxicchance = builder
                .comment("Determines how rare the Toxic ability will occur. (Higher values=lower occurance) Default: 4")
                .define("toxicchance", 4d);

        adrenalinechance = builder
                .comment("Determines how rare the Adrinalin ability will occur. (Higher values=lower occurance) Default: 4")
                .define("adrenalinechance", 4d);

        hardenedchance = builder
                .comment("Determines how rare the Harden ability will occur. (Higher values=lower occurance) Default: 4")
                .define("hardenedchance", 4d);

        builder.pop();
    }

    private void buildRarities(ForgeConfigSpec.Builder builder)
    {
        builder.push("rarities");

        basicChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.5")
                .define("basicChance", 0.5);

        uncommonChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.18")
                .define("uncommonChance", 0.18);

        rareChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.1")
                .define("rareChance", 0.1);

        ultraRareChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.05")
                .define("ultraRareChance", 0.05);

        legendaryChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.02")
                .define("legendaryChance", 0.02);

        archaicChance = builder
                .comment("Sets the chance the given rarity will be applied. Default: 0.01")
                .define("archaicChance", 0.01);

        builder.pop();
    }

    private void buildMultiplier(ForgeConfigSpec.Builder builder)
    {
        builder.push("multiplier");

        basicDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0")
                .define("basicDamage", 0D);

        uncommonDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0.155")
                .define("uncommonDamage", 0.155);

        rareDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0.305")
                .define("rareDamage", 0.305);

        ultraRareDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0.38")
                .define("ultraRareDamage", 0.38);

        legendaryDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0.57")
                .define("legendaryDamage", 0.57);

        archaicDamage = builder
                .comment("Sets the effectiveness for the given rarity. Default: 0.81")
                .define("archaicDamage", 0.81);

        builder.pop();
    }
}
