package com.watergenie3;

import java.util.Map;

import com.watergenie3.settings.CarpetGenieConfig;
import com.watergenie3.settings.CarpetGenieSettings;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.api.settings.SettingsManager;
import carpet.utils.Translations;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class CarpetGenieExtension implements CarpetExtension, ModInitializer {
    
    public static final String MOD_ID = "carpetgenie";
    public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
    public static final String MOD_VERSION = MOD_CONTAINER.getMetadata().getVersion().toString();
    public static final String MOD_NAME = MOD_CONTAINER.getMetadata().getName();

    private static SettingsManager settingsManager;

	public CarpetGenieExtension() {
		CarpetServer.manageExtension(this);
	}

    @Override
    public void onInitialize() {
        settingsManager = new SettingsManager(MOD_VERSION, MOD_ID, MOD_NAME);
        AutoConfig.register(CarpetGenieConfig.class, Toml4jConfigSerializer::new);
    }
	
    @Override
    public void onGameStarted() {
        settingsManager.parseSettingsClass(CarpetGenieSettings.class);
    }

    @Override
    public SettingsManager extensionSettingsManager() {
        return settingsManager;
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return Translations.getTranslationFromResourcePath(String.format("assets/%s/carpet/lang/%s.json", MOD_ID, lang));
    }

    @Override
    public String version() {
        return MOD_ID + " " + MOD_VERSION;
    }

}
