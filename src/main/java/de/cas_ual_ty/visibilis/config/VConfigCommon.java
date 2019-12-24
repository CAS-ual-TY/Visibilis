package de.cas_ual_ty.visibilis.config;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class VConfigCommon
{
    public static final String KEY_SHUTDOWN = "shutdown";
    
    public final BooleanValue shutdown;
    
    public VConfigCommon(Builder builder)
    {
        builder.push("general");
        
        this.shutdown = builder
                        .comment("Shutdown all print activity; No more visual code is being executed.")
                        .translation(Visibilis.MOD_ID + ".config." + VConfigCommon.KEY_SHUTDOWN)
                        .define(VConfigCommon.KEY_SHUTDOWN, false);
        
        builder.pop();
    }
}
