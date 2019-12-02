package de.cas_ual_ty.visibilis.event;

import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemPrintValidationEvent extends Event
{
    public final ItemStack itemStack;
    public final Print print;
    
    public ItemPrintValidationEvent(ItemStack itemStack, Print print)
    {
        this.itemStack = itemStack;
        this.print = print;
    }
    
    @Override
    public boolean isCancelable()
    {
        return true;
    }
}
