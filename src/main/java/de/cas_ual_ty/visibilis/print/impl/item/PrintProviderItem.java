package de.cas_ual_ty.visibilis.print.impl.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.impl.PrintProviderNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

public class PrintProviderItem extends PrintProviderNBT
{
    public ItemStack itemStack;
    public Hand hand;
    
    public PrintProviderItem(ItemStack itemStack, Hand hand)
    {
        super();
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
        Visibilis.channel.sendToServer(new MessageItem(this.itemStack, this.hand));
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt0)
    {
        super.writeToNBT(nbt0);
    }
    
    @Override
    public void saveChange()
    {
        
    }
    
    @Override
    public void undo()
    {
        
    }
    
    @Override
    public void redo()
    {
        
    }
    
    @Override
    public boolean canUndo()
    {
        return false;
    }
    
    @Override
    public boolean canRedo()
    {
        return false;
    }
}
