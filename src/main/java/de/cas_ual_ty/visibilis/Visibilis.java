package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.handler.VEventHandler;
import de.cas_ual_ty.visibilis.handler.VGuiHandler;
import de.cas_ual_ty.visibilis.proxy.ISidedProxy;
import de.cas_ual_ty.visibilis.test.VItemTest;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Visibilis.MOD_ID, name = Visibilis.MOD_NAME, version = Visibilis.MOD_VERSION)
public class Visibilis
{
	public static final String MOD_ID = "visibilis";
	public static final String MOD_NAME = "Visibilis";
	public static final String MOD_VERSION = "1.0.0.0";
	
//	@GameRegistry.ObjectHolder(MOD_ID + ":" + "test")
	public static VItemTest itemTest = (VItemTest) new VItemTest().setUnlocalizedName(MOD_ID + ":" + "test").setRegistryName(MOD_ID + ":" + "test");
	
	@Instance
	public static Visibilis instance;
	
	@SidedProxy(modId = MOD_ID, clientSide = "de.cas_ual_ty.visibilis.proxy.VProxyClient", serverSide = "de.cas_ual_ty.visibilis.proxy.VProxyServer")
	public static ISidedProxy proxy;
	
	public static VEventHandler eventHandler;
	public static VGuiHandler guiHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register((eventHandler = new VEventHandler()));
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, (guiHandler = new VGuiHandler()));
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	//TODO low: Some nice logging here please
	public static void error(String s)
	{
		System.err.println("[" + MOD_ID + "] " + s);
	}
	
	@EventBusSubscriber(modid = MOD_ID)
	public static class Registries
	{
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event)
		{
			event.getRegistry().register(itemTest);
		}
	}
}
