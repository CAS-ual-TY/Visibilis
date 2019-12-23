package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.PrintProviderNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PrintProviderItem extends PrintProviderNBT
{
    public ItemStack itemStack;
    public int slot;
    
    public PrintProviderItem(ItemStack itemStack, int slot)
    {
        super();
        
        this.itemStack = itemStack;
        this.slot = slot;
    }
    
    @Override
    public CompoundNBT getNBT()
    {
        return this.itemStack.getOrCreateTag();
    }
    
    @Override
    public Print createNewPrint()
    {
        return new Print();
    }
    
    @Override
    public void synchToServer(CompoundNBT nbt)
    {
        Visibilis.channel.sendToServer(new MessageItem(this.slot, this.itemStack));
    }
}
