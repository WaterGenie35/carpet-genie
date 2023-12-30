package com.watergenie3.settings;

import static carpet.api.settings.RuleCategory.FEATURE;
import static carpet.api.settings.RuleCategory.SURVIVAL;

import carpet.api.settings.Rule;

public class CarpetGenieSettings {
    public static final String CARPETGENIE = "carpetgenie";
    public static final String SPAWNING = "spawning";
    public static final String ANVIL = "anvil";

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
    public static boolean disableHostileMobGriefing = false;

    @Rule(categories = {CARPETGENIE, FEATURE, SURVIVAL, ANVIL})
    public static boolean enchantmentCostsDoNotCountTowardsLevelCap = false;

    @Rule(categories = {CARPETGENIE, FEATURE, SURVIVAL, ANVIL})
    public static boolean upgradingEnchantmentsDoesNotIncreaseAnvilUse = false;

}
