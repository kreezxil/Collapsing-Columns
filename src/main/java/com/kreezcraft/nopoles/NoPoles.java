package com.kreezcraft.nopoles;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = NoPoles.NAME, modid = NoPoles.MODID, version = NoPoles.VERSION, acceptableRemoteVersions = "*", dependencies = "required-after:forge@[14.23.5.2847,)")
public class NoPoles {
    public static final String MODID = "nopoles";
    public static final String NAME = "No POles";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance
    public static NoPoles instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PoleHandler());
    }
}