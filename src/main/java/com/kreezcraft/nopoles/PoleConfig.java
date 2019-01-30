package com.kreezcraft.nopoles;

import net.minecraftforge.common.config.Config;

@Config(modid = NoPoles.MODID, category = "")
public class PoleConfig {

	@Config.Comment({ "Exempt Blocks" })
	@Config.Name("Blocks")
	public static Whitelist whitelist = new Whitelist();

	public static class Whitelist {
		@Config.Comment({ "Please enter blocks to be ignored 1 per entry", "the format is modid:block_name:dmg\nIf the block has no meta or dmg value then give it a 0",
				"example:", "notenoughscaffold:wooden_scaffold" })
		public String[] blocks = {"notenoughscaffold:wooden_scaffold:0","notenoughscaffold:iron_scaffold:0","minecraft:sand:0","minecraft:sand:1","minecraft:gravel:0"};
	}
}
