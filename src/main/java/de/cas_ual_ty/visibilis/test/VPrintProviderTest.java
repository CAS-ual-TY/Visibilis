package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.item.PrintProviderItem;
import net.minecraft.item.ItemStack;

public class VPrintProviderTest extends PrintProviderItem
{
    public VPrintProviderTest(ItemStack itemStack, int slot)
    {
        super(new NodeListProviderBase(), itemStack, slot);
    }
}
