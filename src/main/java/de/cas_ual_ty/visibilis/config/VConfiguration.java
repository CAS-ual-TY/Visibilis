package de.cas_ual_ty.visibilis.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class VConfiguration
{
    public static boolean shutdown = false;
    
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;
    
    public static final VConfigClient CLIENT;
    public static final VConfigCommon COMMON;
    
    static
    {
        Pair<VConfigClient, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(VConfigClient::new);
        CLIENT = client.getLeft();
        CLIENT_SPEC = client.getRight();
        
        Pair<VConfigCommon, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(VConfigCommon::new);
        COMMON = common.getLeft();
        COMMON_SPEC = common.getRight();
    }
}