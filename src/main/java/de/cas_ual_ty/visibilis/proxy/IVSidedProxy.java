package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.print.impl.PrintProvider;

public interface IVSidedProxy
{
    public default void openGuiPrint(PrintProvider helper)
    {
    }
}
