package com.kreezcraft.nopoles;

import com.kreezcraft.nopoles.proxy.IProxy;
import com.kreezcraft.nopoles.utility.LogHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(name = NoPoles.NAME, modid = NoPoles.MODID, version = NoPoles.VERSION, guiFactory = NoPoles.GUI_FACTORY_CLASS)
public class NoPoles {
	public static final String MODID = "nopoles";
	public static final String NAME = "No POles";
	public static final String VERSION = "@VERSION@";
	
	public static final String CLIENT_PROXY_CLASS = "com.kreezcraft.nopoles.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.kreezcraft.nopoles.proxy.ServerProxy";
	
	public static final String GUI_FACTORY_CLASS = "com.kreezcraft.nopoles.client.gui.GuiFactory";
	
	@Mod.Instance(MODID)
	public static NoPoles instance = new NoPoles();
	
	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		LogHelper.info("NoPoles Pre-initializing");
		
		PoleConfig.init(e.getModConfigurationDirectory().toString());
		FMLCommonHandler.instance().bus().register(new PoleConfig());
		
		MinecraftForge.EVENT_BUS.register(new PoleHandler());

		LogHelper.info("NoPoles Pre-initializing Done");

	}

	@EventHandler
	public void init(FMLInitializationEvent e) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		LogHelper.info("NoPoles has fully initialized");
	}

}