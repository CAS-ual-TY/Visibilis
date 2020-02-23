package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.item.ItemPrintClickable;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;

public class VItemCodePrint extends ItemPrintClickable
{
    public VItemCodePrint(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean isEditable(ItemStack itemStack, CommandSource source)
    {
        return true;
    }
}
