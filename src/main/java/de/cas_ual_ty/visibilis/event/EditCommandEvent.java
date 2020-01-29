package de.cas_ual_ty.visibilis.event;

import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.Event;

public class EditCommandEvent extends Event
{
    public final CommandSource source;
    
    public EditCommandEvent(CommandSource source)
    {
        this.source = source;
    }
    
    @Override
    public boolean isCancelable()
    {
        return true;
    }
}
