package com.kreezcraft.nopoles;

import java.util.Arrays;

import com.kreezcraft.nopoles.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class PoleHandler {

	private static void dm(String msg) {
		if (PoleConfig.debugMode) {
			LogHelper.info(msg);
		}
	}

	@SubscribeEvent
	public void NoColumns(PlaceEvent event) {
		dm("Entered PlaceEvent");
		EntityPlayer player = event.player;
		dm("got the player");

		if (player.onGround || player.isOnLadder() || player.capabilities.allowFlying || player.isInWater()) {

			dm("player is on ground, ladder, in water, or flying");
			return;
		}

		World world = event.world;
		int bpX = event.x;
		int bpY = event.y;
		int bpZ = event.z;

		dm("got some world data");

//		double d0 = bpX;
//		double d1 = bpY;
//		double d2 = bpZ;

		Block blockstate = world.getBlock(bpX, bpY, bpZ);

		dm("got the block that triggered this event");
		// If player is building from underneath the block being placed, then allow the
		// construction
		if (player.posY < bpY)
			return;
		dm("got the player's y position");

		if (Arrays.asList(PoleConfig.whiteList)
				.contains(Block.getIdFromBlock(blockstate) + ":" + blockstate.getDamageValue(world, bpX, bpY, bpZ))) {
			if (PoleConfig.debugMode) {
				System.out.println("Block is safe");
			}
			return;
		}

		boolean destroy = true;
		int checkX = bpX;
		int checkY = bpY;
		int checkZ = bpZ;

		for (int i = 0; i < PoleConfig.poleHeight; i++) {
			if (PoleConfig.debugMode) {
				System.out.println("checking block #" + i);
			}

			if (!world.isAirBlock(checkX - 1, checkY, checkZ) || !world.isAirBlock(checkX + 1, checkY, checkZ)
					|| !world.isAirBlock(checkX, checkY, checkZ - 1) || !world.isAirBlock(checkX, checkY, checkZ + 1)) {
				// do nothing, not a nerdpole
				destroy = false;
				return;

			}
			checkY = checkY - 1;
		}

		if (PoleConfig.debugMode) {
			System.out.println("column is a nerdpole? [" + destroy + "]");
		}

		if (destroy) {
			checkX = bpX;
			checkY = bpY;
			checkZ = bpZ;

			while (world.isAirBlock(checkX - 1, checkY, checkZ) || world.isAirBlock(checkX + 1, checkY, checkZ)
					|| world.isAirBlock(checkX, checkY, checkZ - 1) || world.isAirBlock(checkX, checkY, checkZ + 1)) {
				if (PoleConfig.debugMode) {
					System.out.println("Destroying pole at position x:" + checkX + " y:" + checkY + " z:" + checkZ);
				}
				ItemStack returnStack = new ItemStack(world.getBlock(checkX, checkY, checkZ));
				EntityItem damnPole = new EntityItem(world, checkX, checkY, checkZ, returnStack);
				world.spawnEntityInWorld(damnPole);
				world.setBlockToAir(checkX, checkY, checkZ);
				checkY = checkY - 1;
			}
		}
// is or was this even necessary?
//		ItemStack stack = new ItemStack(blockstate.getBlock());
//		EntityItem haha = new EntityItem(world, d0, d1, d2, stack);
//
//		world.spawnEntityInWorld(haha);
//
//		world.setBlockToAir(event.getPos());

	}

}
