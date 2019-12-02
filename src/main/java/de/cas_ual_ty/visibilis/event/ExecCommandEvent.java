package de.cas_ual_ty.visibilis.event;

import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.Event;

public class ExecCommandEvent extends Event
{
    public final CommandSource source;
    
    public ExecCommandEvent(CommandSource source)
    {
        this.source = source;
    }
}
