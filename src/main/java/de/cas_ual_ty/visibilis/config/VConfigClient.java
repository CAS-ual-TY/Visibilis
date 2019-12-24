package de.cas_ual_ty.visibilis.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class VConfigClient
{
    public VConfigClient(final ForgeConfigSpec.Builder builder)
    {
        builder.push("client");
        
        builder.pop();
    }
}
