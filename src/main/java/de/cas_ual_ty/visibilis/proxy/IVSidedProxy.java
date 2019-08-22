package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.print.IPrintHelper;

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
    
    public default void openGuiPrint(IPrintHelper helper)
    {
    }
}
