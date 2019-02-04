package com.kreezcraft.nopoles;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
//		Block block = event.getPlacedBlock().getBlock();
		IBlockState blockstate = world.getBlockState(position);

		if (Arrays.asList(PoleConfig.whitelist.blocks)
				.contains(blockstate.getBlock().getRegistryName().toString() + ":" + blockstate.getBlock().getMetaFromState(blockstate))) {
			// System.out.println("Block is safe");
			return;
		}


		BlockPos check = position;
		boolean destroy = true;
		
		for (int i = 0; i < PoleConfig.nerdpole.poleHeight; i++) {
			//System.out.println("checking block #" + i);
			if (!world.isAirBlock(check.south()) || !world.isAirBlock(check.north()) || !world.isAirBlock(check.west())
					|| !world.isAirBlock(check.east())) {
				// do nothing, not a nerdpole
				destroy=false;
				return;
				
			}
			check = check.down();
		}
		
//		System.out.println("column is a nerdpole? ["+destroy+"]");
		
		if(destroy) {
			check = position;
			while(world.isAirBlock(check.south()) && world.isAirBlock(check.north()) && world.isAirBlock(check.west())
					&& world.isAirBlock(check.east())) {
				System.out.println("Destroying pole at position "+check.toString());
				ItemStack returnStack = new ItemStack(world.getBlockState(check).getBlock());
				EntityItem damnPole = new EntityItem(world, check.getX(), check.getY(), check.getZ(), returnStack);
				world.spawnEntity(damnPole);
				world.setBlockToAir(check);
				check = check.down();
			}
		}

		ItemStack stack = new ItemStack(blockstate.getBlock());
		EntityItem haha = new EntityItem(world, d0, d1, d2, stack);

		world.spawnEntity(haha);

		world.setBlockToAir(event.getPos());

	}

}
