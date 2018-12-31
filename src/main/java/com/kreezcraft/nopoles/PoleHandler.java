package com.kreezcraft.nopoles;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PoleHandler {

	@SubscribeEvent
	public static void NoColumns(PlaceEvent event) {
		
		EntityPlayer player = event.getPlayer();

		if (player.onGround || player.isOnLadder() || player.capabilities.allowFlying || player.isInWater())
			return;

		World world = event.getWorld();
		BlockPos position = event.getPos();
		double d0 = position.getX();
		double d1 = position.getY();
		double d2 = position.getZ();
		Block block = event.getPlacedBlock().getBlock();
		ItemStack stack = new ItemStack(Item.getItemFromBlock(block));
		EntityItem haha = new EntityItem(world, d0, d1 ,d2, stack);
		
		world.spawnEntity(haha);
		
		event.getWorld().setBlockToAir(event.getPos());

	}

}
