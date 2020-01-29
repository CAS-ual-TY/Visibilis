package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.PrintProvider;
import de.cas_ual_ty.visibilis.print.item.ItemPrintClickable;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;

public class VItemTest extends ItemPrintClickable
{
    public VItemTest(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public PrintProvider getPrintProvider(ItemStack itemStack, int slot)
    {
        return new VPrintProviderTest(itemStack, slot);
    }
    
    @Override
    public boolean isEditable(ItemStack itemStack, CommandSource source)
    {
        return true;
    }
}
