package com.kreezcraft.nopoles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

@Mod.EventBusSubscriber
public class PoleHandler {

    private void dm(EntityPlayer player, String msg) {
        if (PoleConfig.debugmode.debugMode)
            player.sendMessage(new TextComponentString(msg));
    }

    @SubscribeEvent
    public void NoColumns(BlockEvent.EntityPlaceEvent event) {

        EntityPlayer player = (EntityPlayer) event.getEntity();
        dm(player,"I got you babe!");
        if (player.onGround || player.isOnLadder() || player.capabilities.allowFlying || player.isInWater())
            return;

        World world = event.getWorld();
        BlockPos position = event.getPos();
        double d0 = position.getX();
        double d1 = position.getY();
        double d2 = position.getZ();

        IBlockState blockstate = world.getBlockState(position);

        //If player is building from underneath the block being placed, then allow the construction
        if (player.getPosition().getY() < position.getY()) return;

        dm(player, "Block being placed is: " + blockstate.getBlock().getRegistryName().toString() + ":" + blockstate.getBlock().getMetaFromState(blockstate));
        if (Arrays.asList(PoleConfig.whitelist.blocks)
                .contains(blockstate.getBlock().getRegistryName().toString() + ":" + blockstate.getBlock().getMetaFromState(blockstate))) {
            dm(player, "Block is safe");
            return;
        }


        BlockPos check = position;
        boolean destroy = true;

        for (int i = 0; i < PoleConfig.nerdpole.poleHeight; i++) {
            dm(player, "checking block #" + i);
            if (!world.isAirBlock(check.south()) || !world.isAirBlock(check.north()) || !world.isAirBlock(check.west())
                    || !world.isAirBlock(check.east())) {
                // do nothing, not a nerdpole
                destroy = false;
                return;

            }
            check = check.down();
        }

        dm(player, "column is a nerdpole? [" + destroy + "]");

        if (destroy) {
            check = position;
            while (world.isAirBlock(check.south()) && world.isAirBlock(check.north()) && world.isAirBlock(check.west())
                    && world.isAirBlock(check.east())) {
                System.out.println("Destroying pole at position " + check.toString());
                ItemStack returnStack = new ItemStack(world.getBlockState(check).getBlock());
                EntityItem damnPole = new EntityItem(world, check.getX(), check.getY(), check.getZ(), returnStack);
                world.spawnEntity(damnPole);
                world.setBlockToAir(check);
                check = check.down();
            }
        }

    }

}
