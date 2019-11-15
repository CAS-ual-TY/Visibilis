package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.PrintHelperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

public class PrintHelperItem extends PrintHelperBase
{
    public ItemStack itemStack;
    public Hand hand;
    
    public PrintHelperItem(ItemStack itemStack, Hand hand)
    {
        if (!itemStack.hasTag())
        {
            itemStack.setTag(new CompoundNBT());
        }
        
        this.itemStack = itemStack;
        this.hand = hand;
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
        super.synchToServer(nbt);
        Visibilis.channel.sendToServer(new MessageItem(this.itemStack, this.hand));
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt0)
    {
        super.writeToNBT(nbt0);
    }
}
