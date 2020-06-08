package de.cas_ual_ty.visibilis.config;

import net.minecraftforge.fml.config.ModConfig;

public class VConfigHelper
{
    // We store a reference to the ModConfigs here to be able to change the values in them from our code
    // (For example from a config GUI)
    
    public static ModConfig clientConfig;
    public static ModConfig commonConfig;
    
    public static void bake(ModConfig config)
    {
        if(config.getSpec() == VConfiguration.CLIENT_SPEC)
        {
            VConfigHelper.bakeClient(config);
        }
        else if(config.getSpec() == VConfiguration.COMMON_SPEC)
        {
            VConfigHelper.bakeServer(config);
        }
    }
    
    public static void bakeClient(ModConfig config)
    {
        VConfigHelper.clientConfig = config;
        VConfiguration.CLIENT.overrideColors();
    }
    
    public static void bakeServer(ModConfig config)
    {
        VConfigHelper.commonConfig = config;
        VConfiguration.shutdown = VConfiguration.COMMON.shutdown.get();
    }
    
    public static void setValueAndSave(ModConfig modConfig, String path, Object newValue)
    {
        modConfig.getConfigData().set(path, newValue);
        modConfig.save();
    }
}
