package de.cas_ual_ty.mundusmagicus;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=MundusMagicus.MOD_ID, name=MundusMagicus.MOD_NAME, version=MundusMagicus.MOD_VERSION)
public class MundusMagicus
{
	public static final String MOD_ID = "mundusmagicus";
	public static final String MOD_NAME = "Mundus Magicus";
	public static final String MOD_VERSION = "1.0.0.0";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	public static void warn(String s)
	{
		System.err.println(s);
	}
}
