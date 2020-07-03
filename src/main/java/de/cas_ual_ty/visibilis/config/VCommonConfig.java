package de.cas_ual_ty.visibilis.config;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class VCommonConfig
{
    public static final String KEY_SHUTDOWN = "shutdown";
    
    public final BooleanValue shutdown;
    
    public VCommonConfig(Builder builder)
    {
        builder.push("general");
        
        this.shutdown = builder
            .comment("Shutdown all print activity; No more visual code is being executed.")
            .translation(Visibilis.MOD_ID + ".config." + VCommonConfig.KEY_SHUTDOWN)
            .define(VCommonConfig.KEY_SHUTDOWN, false);
        
        builder.pop();
    }
}
