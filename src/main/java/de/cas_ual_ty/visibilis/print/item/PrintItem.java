package de.cas_ual_ty.visibilis.print.item;

import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PrintItem extends Item implements IPrintItem
{
    public PrintItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt)
    {
        return true;
    }
    
    // is this item editable via the "v edit" command?
    public boolean isEditable(ItemStack itemStack, CommandSource source)
    {
        return false;
    }
}
