package com.kreezcraft.nopoles;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.sun.org.apache.xerces.internal.xs.StringList;
import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class PoleConfig {

    public static final String CAT_GEN = "general";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.BooleanValue debugMode;
    public static ForgeConfigSpec.IntValue poleHeight;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> whiteList;

    static {
        COMMON_BUILDER.comment("General Settings").push(CAT_GEN);
        COMMON_BUILDER.pop();

        setupConfigBlock();

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupConfigBlock() {
        debugMode = COMMON_BUILDER.comment("If you don't think it is working, turn on debug mode")
                .define("debugMode", false);
		poleHeight = COMMON_BUILDER.comment("At what height is it a nerdpole? Default: 10")
				.defineInRange("poleHeight",10,0,256);
		//List<String> blocks = Arrays.asList(new String[]{"minecraft:sand", "minecraft:redsand", "minecraft:gravel"});
		whiteList = COMMON_BUILDER.comment("Lost of blockID's to allow in making poles.","Recommend adding scaffolding, gravel, and sand variants as those can","easily be taken down.","use the format modid:blockid")
				.defineList("whiteList",Arrays.asList(new String[]{"minecraft:sand", "minecraft:redsand", "minecraft:gravel"}),s -> s instanceof String);
    }


	public static void loadConfig(ForgeConfigSpec spec, Path path) {

		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.ConfigReloading configEvent) {
	}
}
