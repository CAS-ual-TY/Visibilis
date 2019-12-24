package de.cas_ual_ty.visibilis.config;

import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class VConfigClient
{
    public VConfigClient(Builder builder)
    {
        builder.push("client");
        
        builder.pop();
    }
}
