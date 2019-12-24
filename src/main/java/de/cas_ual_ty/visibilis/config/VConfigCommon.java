package de.cas_ual_ty.visibilis.config;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraftforge.common.ForgeConfigSpec;

public class VConfigCommon
{
    public final ForgeConfigSpec.BooleanValue shutdown;
    
    public VConfigCommon(final ForgeConfigSpec.Builder builder)
    {
        builder.push("general");
        
        this.shutdown = builder
                        .comment("Shutdown all print activity; No more visual code is being executed.")
                        .translation(Visibilis.MOD_ID + ".config.shutdown")
                        .define("shutdown", false);
        
        builder.pop();
    }
}
