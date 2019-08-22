package de.cas_ual_ty.visibilis.print;

import de.cas_ual_ty.visibilis.Visibilis;
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
