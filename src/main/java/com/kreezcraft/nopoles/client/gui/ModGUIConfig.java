package com.kreezcraft.nopoles.client.gui;

import com.kreezcraft.nopoles.NoPoles;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import static com.kreezcraft.nopoles.PoleConfig.*;

public class ModGUIConfig extends GuiConfig {

	public ModGUIConfig(GuiScreen guiScreen) {
		super(guiScreen,
				new ConfigElement<Object>(getConfiguration().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				NoPoles.MODID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(getConfiguration().toString())
				);
	}
}
