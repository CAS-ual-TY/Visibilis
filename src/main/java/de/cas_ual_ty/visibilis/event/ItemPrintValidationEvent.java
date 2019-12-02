package de.cas_ual_ty.visibilis.event;

import de.cas_ual_ty.visibilis.print.Print;
import net.minecraftforge.eventbus.api.Event;

public class ItemPrintValidationEvent extends Event
{
    public final Print print;
    
    public ItemPrintValidationEvent(Print print)
    {
        this.print = print;
    }
    
    @Override
    public boolean isCancelable()
    {
        return true;
    }
}
