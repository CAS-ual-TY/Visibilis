package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.PrintHelperItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class VPrintHelperTest extends PrintHelperItem
{
    public VPrintHelperTest(ItemStack itemStack, EnumHand hand)
    {
        super(itemStack, hand);
    }
    
    @Override
    public Print createNewPrint()
    {
        return new VPrintTest();
    }
}
