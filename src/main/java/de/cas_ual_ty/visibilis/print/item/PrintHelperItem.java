package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.PrintHelperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class PrintHelperItem extends PrintHelperBase
{
    public ItemStack itemStack;
    public EnumHand hand;
    
    public PrintHelperItem(ItemStack itemStack, EnumHand hand)
    {
        if (!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        
        this.itemStack = itemStack;
        this.hand = hand;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt0)
    {
        super.writeToNBT(nbt0);
        
        // Synch to server
        Visibilis.channel.sendToServer(new MessageItem(this.itemStack, this.hand));
    }
    
    @Override
    public NBTTagCompound getNBT()
    {
        return this.itemStack.getTagCompound();
    }
    
    @Override
    public Print createNewPrint()
    {
        return new Print();
    }
}
