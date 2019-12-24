package de.cas_ual_ty.visibilis.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;;

public class VConfiguration
{
    public static boolean shutdown = false;
    
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;
    
    public static final VConfigClient CLIENT;
    public static final VConfigCommon COMMON;
    
    static
    {
        Pair<VConfigClient, ForgeConfigSpec> client = new Builder().configure(VConfigClient::new);
        CLIENT = client.getLeft();
        CLIENT_SPEC = client.getRight();
        
        Pair<VConfigCommon, ForgeConfigSpec> common = new Builder().configure(VConfigCommon::new);
        COMMON = common.getLeft();
        COMMON_SPEC = common.getRight();
    }
}