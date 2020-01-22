package com.kreezcraft.nopoles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber
public class PoleHandler {
    private void dm(String msg) {
        if (PoleConfig.debugMode.get().booleanValue()) {
            System.out.println(msg);
        }
    }

    @SubscribeEvent
    public void NoColumns(PlayerInteractEvent event) {

        PlayerEntity player = event.getPlayer();
        if (player == null) {
            return;
        }

        if (player.onGround || player.isOnLadder() || player.isAirBorne || player.isCreative() || player.isInWater()) {
            dm("Player is creative, on the ground, on a ladder, in the air, or in water");
            return;
        }

        World world = event.getWorld();
        BlockPos position = event.getPos();
        double d0 = position.getX();
        double d1 = position.getY();
        double d2 = position.getZ();

        BlockState blockstate = world.getBlockState(position);

        //If player is building from underneath the block being placed, then allow the construction
        if (player.getPosition().getY() < position.getY()) return;

        if (Arrays.asList(PoleConfig.whiteList.get())
                //.contains(blockstate.getBlock().getRegistryName().toString() + ":" + blockstate.getBlock().getMetaFromState(blockstate))) {
                .contains(blockstate.getBlock().getRegistryName().toString())) {
            dm("Block is safe");
            return;
        }


        BlockPos check = position;
        boolean destroy = true;

        for (int i = 0; i < PoleConfig.poleHeight.get().intValue(); i++) {
            //System.out.println("checking block #" + i);
            if (!world.isAirBlock(check.south()) || !world.isAirBlock(check.north()) || !world.isAirBlock(check.west())
                    || !world.isAirBlock(check.east())) {
                // do nothing, not a nerdpole
                destroy = false;
                return;

            }
            check = check.down();
        }

        dm("column is a nerdpole? [" + destroy + "]");

        if (destroy) {
            check = position;
            while (world.isAirBlock(check.south()) && world.isAirBlock(check.north()) && world.isAirBlock(check.west())
                    && world.isAirBlock(check.east())) {
                System.out.println("Destroying pole at position " + check.toString());
                ItemStack returnStack = new ItemStack(world.getBlockState(check).getBlock());
                ItemEntity damnPole = new ItemEntity(world, check.getX(), check.getY(), check.getZ(), returnStack);

                
                world.spawnEntity(damnPole);
                world.setBlockToAir(check);
                check = check.down();
            }
        }
//
//        ItemStack stack = new ItemStack(blockstate.getBlock());
//        EntityItem haha = new EntityItem(world, d0, d1, d2, stack);
//
//        world.spawnEntity(haha);
//
//        world.setBlockToAir(event.getPos());
//    } else
//
//    {
//        return;
//    }

    }
}