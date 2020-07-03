package de.cas_ual_ty.visibilis.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;;

public class VConfiguration
{
    public static boolean shutdown = false;
    
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;
    
    public static final VClientConfig CLIENT;
    public static final VCommonConfig COMMON;
    
    static
    {
        Pair<VClientConfig, ForgeConfigSpec> client = new Builder().configure(VClientConfig::new);
        CLIENT = client.getLeft();
        CLIENT_SPEC = client.getRight();
        
        Pair<VCommonConfig, ForgeConfigSpec> common = new Builder().configure(VCommonConfig::new);
        COMMON = common.getLeft();
        COMMON_SPEC = common.getRight();
    }
}