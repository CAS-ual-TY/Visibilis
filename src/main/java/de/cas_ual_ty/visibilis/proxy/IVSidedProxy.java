package de.cas_ual_ty.visibilis.proxy;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public interface IVSidedProxy
{
    public default void preInit()
    {
    }
    
    public default void init()
    {
    }
    
    public default void postInit()
    {
    }
    
    public default void openGuiPrintForItemStack(ItemStack itemStack, EnumHand hand)
    {
    }
}
