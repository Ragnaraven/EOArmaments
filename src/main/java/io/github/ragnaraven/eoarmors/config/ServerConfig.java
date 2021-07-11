package io.github.ragnaraven.eoarmors.config;

import io.github.ragnaraven.eoarmors.core.eventlisteners.EOAEnderObsidianEventHandler;
import net.minecraftforge.common.ForgeConfigSpec;

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
        this.EXP_CHANCE = buildInt(builder, "Experience Chance", "all", 5, 0, 10000, "Base experience for obsidian to drop. Literal value.");
        this.BASE_EXP = buildInt(builder, "Base Exp", "all", 2, 0, 10000, "Base experience for obsidian to drop. Literal value.");
        this.EXP_RANDOM = buildInt(builder, "Base Exp Deviation", "all", 3, 0, 10000, "Additonal random xp. Literal value.");

        this.OBSIDIAN_TO_MINE_FULL_HEALTH_PICKAXE = buildInt(builder, "Obsidian to mine to full Pick Health", "all", 25, 0, 10000, "Number of obsidian to mine to heal a pick to full health. Literal value.");
        this.OBSIDIAN_TO_MINE_FULL_HEALTH_INVENTORY = buildInt(builder, "Obsidian to mine to full inventory health", "all", 18, 0, 10000, "Number of obsidian to mine to heal a non-armor tools that are not picks to full health while mining obsidian. Literal value added to obsidianToMineUntilFullPickHealth.");

        this.HEAL_ARMOR_CHANCE = buildInt(builder, "Armor Heal Chance on mine", "all", 3, 0, 10000, "Chance to heal armor when mining obsidian. 1 in AMOUNT chance.");
        this.HEAL_ARMOR_DURABILITY = buildInt(builder, "Armor Heal Amount", "all", 2, 0, 10000, "How much durability to heal armor when mining obsidian.");
        this.HEAL_ARMOR_EXP_DROP_AMOUNT = buildInt(builder, "Armor Heal Exp Drop Chance (always 1xp)", "all", 1, 0, 10000, "Chance to drop 1 xp when mining obsidian and armor is healed.");
        this.CHANCE_TO_SILK_TOUCH_OBSIDIAN = buildInt(builder, "Chance to Silk Touch Obsidian and Drop instead of Consuming", "all", 7, 0, 1000, "Chance to drop obsidian when wearing an O or EO armor set and using a matching pick with silk touch.");
        this.CHANCE_TO_CONVERT_OBSIDIAN_TO_ENDER_OBSIDIAN = buildInt(builder, "Chance to Convert Obsidian to Ender Obsidian while mining in End", "all", 7, 0, 1000, "Chance to drop ender obsidian while in the end with an EO armor set and using a matching pick with silk touch.");

        builder.pop();
        builder.push("Ender Obsidian Spawning");
        this.CHANCE_TO_SPAWN_ENDER_OBSIDIAN_MODS = buildInt(builder, "Chance to Silk Touch Obsidian and Drop instead of Consuming", "all",      EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.defaultVal, EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.min,     EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT_MOD.max, "Chance to spawn ender obsidian with game changing mods detected.");
        this.CHANCE_TO_SPAWN_ENDER_OBSIDIAN_STANDARD = buildInt(builder, "Chance to Convert Obsidian to Ender Obsidian while mining in End", "all", EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.defaultVal,     EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.min,         EOAEnderObsidianEventHandler.CHANCE_SPAWN_ENDER_OBSIDIAN_EVENT.max,     "Chance to spawn ender obsidian without game changing mods detected.");
    }


    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, String catagory, int defaultValue, int min, int max, String comment){
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

}
