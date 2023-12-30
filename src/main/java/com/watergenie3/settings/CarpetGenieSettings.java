package com.watergenie3.settings;

import static carpet.api.settings.RuleCategory.FEATURE;

import carpet.api.settings.Rule;

public class CarpetGenieSettings {
    public static final String SPAWNING = "spawning";

    @Rule(categories = {FEATURE, SPAWNING})
    public static boolean spawnFromFloorHeightmap = false;

    @Rule(categories = {FEATURE, SPAWNING})
    public static boolean spawnHeightmapIgnoresBedrock = false;

}
