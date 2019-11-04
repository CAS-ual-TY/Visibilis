package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.print.IPrintProvider;

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
    
    public default void openGuiPrint(IPrintProvider helper)
    {
    }
}
