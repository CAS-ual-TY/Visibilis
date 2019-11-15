package de.cas_ual_ty.magicamundi;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//@Mod(MagicaMundi.MOD_ID)
public class MagicaMundi
{
    public static final String MOD_ID = "magicamundi";
    
    public MagicaMundi instance;
    
    public MagicaMundi()
    {
        this.instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    public void setup(FMLCommonSetupEvent event)
    {
        
    }
    
    public static void warn(String s)
    {
        System.err.println(s);
    }
}
