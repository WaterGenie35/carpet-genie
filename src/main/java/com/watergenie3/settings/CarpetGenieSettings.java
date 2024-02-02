package com.watergenie3.settings;

import static carpet.api.settings.RuleCategory.FEATURE;
import static carpet.api.settings.RuleCategory.SURVIVAL;

import carpet.api.settings.Rule;

public class CarpetGenieSettings {
    public static final String CARPETGENIE = "carpetgenie";

    public static final String SPAWNING = "spawning";
    public static final String ANVIL = "anvil";
    public static final String MTE = "mte";
    public static final String REDSTONE = "redstone";
    public static final String CLIENT = "client";
    public static final String DEBUG = "debug";
    public static final String FISHING = "fishing";
    public static final String VILLAGER = "villager";

    @Rule(categories = {CARPETGENIE, FEATURE, SPAWNING})
    public static boolean spawnFromFloorHeightmap = false;

    @Rule(categories = {CARPETGENIE, FEATURE, SPAWNING})
    public static boolean spawnHeightmapIgnoresBedrock = false;

    @Rule(
        categories = {CARPETGENIE, FEATURE, SPAWNING},
        options = {"1", "2", "3", "4"},
        strict = true
    )
    public static int witchSwampHutMaxGroupSize = 1;

    @Rule(categories = {CARPETGENIE, FEATURE, SURVIVAL})
    public static boolean hostileMobGriefing = true;

    @Rule(categories = {CARPETGENIE, FEATURE, ANVIL})
    public static boolean enchantmentCostsCountTowardsLevelCap = true;

    @Rule(categories = {CARPETGENIE, FEATURE, ANVIL})
    public static boolean upgradingEnchantmentsIncreaseAnvilUse = true;

    @Rule(categories = {CARPETGENIE, FEATURE, MTE})
    public static boolean immovableNetherWoodTileEntities = false;

    @Rule(categories = {CARPETGENIE, FEATURE, REDSTONE})
    public static boolean repeaterOnBlueIceHas1gtLessDelay = false;

    @Rule(categories = {CARPETGENIE, FEATURE, REDSTONE})
    public static boolean repeaterOnMagentaGlazedTerracottaPowersExtraBlock = false;
    
    @Rule(categories = {CARPETGENIE, FEATURE, CLIENT})
    public static boolean highlightTargetFloor = false;
    
    @Rule(categories = {CARPETGENIE, DEBUG, FISHING})
    public static boolean debugFishing = false;

    @Rule(categories = {CARPETGENIE, DEBUG, VILLAGER})
    public static boolean debugVillagerWorkstation = false;

    @Rule(categories = {CARPETGENIE, DEBUG, VILLAGER})
    public static boolean debugVillagerBed = false;

}
