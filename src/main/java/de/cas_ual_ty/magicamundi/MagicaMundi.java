package de.cas_ual_ty.magicamundi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MagicaMundi.MOD_ID, name = MagicaMundi.MOD_NAME, version = MagicaMundi.MOD_VERSION)
public class MagicaMundi
{
    public static final String MOD_ID = "magicamundi";
    public static final String MOD_NAME = "Magica Mundi";
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
