package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.NodeListProvider;
import de.cas_ual_ty.visibilis.print.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.PrintProviderItem;
import net.minecraft.item.ItemStack;

public class VPrintProviderTest extends PrintProviderItem
{
    public VPrintProviderTest(NodeListProvider nodeListProvider, ItemStack itemStack, int slot)
    {
        super(nodeListProvider, itemStack, slot);
    }
    
    public VPrintProviderTest(ItemStack itemStack, int slot)
    {
        super(new NodeListProviderBase(), itemStack, slot);
    }
    
    @Override
    public Print createNewPrint()
    {
        return new Print();
    }
}
