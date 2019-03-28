package com.kreezcraft.nopoles;

import net.minecraftforge.common.config.Config;

@Config(modid = NoPoles.MODID, category = "")
public class PoleConfig {

	@Config.Comment({ "Exempt Blocks" })
	@Config.Name("Blocks")
	public static Whitelist whitelist = new Whitelist();
	
	@Config.Comment({"Pole Identification aka what defines the pole?"})
	@Config.Name("Nerd Pole")
	public static NerdPole nerdpole = new NerdPole();

	public static class Whitelist {
		@Config.Comment({ "Please enter blocks to be ignored 1 per entry", "the format is modid:block_name:dmg\nIf the block has no meta or dmg value then give it a 0",
				"example:", "notenoughscaffold:wooden_scaffold" })
		public String[] blocks = {"notenoughscaffold:wooden_scaffold:0","notenoughscaffold:iron_scaffold:0","minecraft:sand:0","minecraft:sand:1","minecraft:gravel:0"};
		
	}
	
	public static class NerdPole {
		
		@Config.Comment({"How many blocks tall is a pole before it is considered a nerdpole?\n","Anything higher than this will cause the pole to be destroyed.\n","Default: 10"})
		public int poleHeight = 10;
		
	}
}