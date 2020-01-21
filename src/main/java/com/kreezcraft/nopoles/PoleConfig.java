package com.kreezcraft.nopoles;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class PoleConfig {

	public static Configuration configuration;

	public static String[] whiteList;
	public static boolean debugMode;
	public static int poleHeight;

	public static void init(String configDir) {
		if (configuration == null) {
			File path = new File(configDir + "/" + NoPoles.MODID + ".cfg");

			configuration = new Configuration(path);
			loadConfiguration();
		}

	}

	private static void loadConfiguration() {
		debugMode = configuration.get(Configuration.CATEGORY_GENERAL, "debugMode", false, "Is debugMode active?")
				.getBoolean();

		whiteList = configuration.get(Configuration.CATEGORY_GENERAL, "whiteList", new String[] {},
				"Only these blocks will be allowed for building straight up.\nSuggest adding the scaffold block ids here.\nformat is ###:#")
				.getStringList();

		poleHeight = configuration.getInt("poleHeight", Configuration.CATEGORY_GENERAL, 10, 0, 256,
				"10 seems like the sane value.");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(NoPoles.MODID)) {
			loadConfiguration();
		}
	}

	public static Configuration getConfiguration() {
		return configuration;
	}
}
