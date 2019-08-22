package de.cas_ual_ty.visibilis.print;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PrintHelperItem extends PrintHelperBase
{
    public ItemStack itemStack;
    
    public PrintHelperItem(ItemStack itemStack)
    {
        if(!itemStack.hasTagCompound())
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        
        this.itemStack = itemStack;
    }
    
    @Override
    public NBTTagCompound getNBT()
    {
        return this.itemStack.getTagCompound();
    }
}
