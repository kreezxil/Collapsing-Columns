package com.kreezcraft.nopoles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

import static com.kreezcraft.nopoles.NoPoles.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PoleHandler {
    private void dm(PlayerEntity p, String msg) {
        if (PoleConfig.debugMode.get()) {
            p.sendMessage(new StringTextComponent(msg));
        }
    }

    @SubscribeEvent
    public void NoColumns(PlayerInteractEvent event) {

        PlayerEntity player = event.getPlayer();
        if (player == null) {
//            dm(player, "I think it's null!");
            return;
        }

        if (player.onGround || player.isOnLadder() || player.isAirBorne || player.isCreative() || player.isInWater()) {
            dm(player, "Player is creative, on the ground, on a ladder, in the air, or in water");
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

        dm(player,"Found Block:"+blockstate.getBlock().getRegistryName().toString());
        if (PoleConfig.whiteList.get()
                .contains(blockstate.getBlock().getRegistryName().toString())) {
            dm(player, "Block is safe");
            return;
        }


        BlockPos check = position;
        boolean destroy = true;

        for (int i = 0; i < PoleConfig.poleHeight.get().intValue(); i++) {
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
                dm(player, "Destroying pole at position " + check.toString());
                ItemStack returnStack = new ItemStack(world.getBlockState(check).getBlock());
                ItemEntity damnPole = new ItemEntity(world, check.getX(), check.getY(), check.getZ(), returnStack);

                world.addEntity(damnPole);
                //world.spawnEntity(damnPole);
                //world.set
                world.setBlockState(check, Blocks.AIR.getDefaultState());
                check = check.down();
            }
        }

    }
}