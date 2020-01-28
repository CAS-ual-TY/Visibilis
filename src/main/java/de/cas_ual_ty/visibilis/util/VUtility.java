package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.print.PrintProvider;

public class VUtility
{
    @SuppressWarnings("unchecked")
    public static <A, B> A cast(B b)
    {
        return (A)b;
    }
    
    public static <A> ArrayList<A> cloneArrayList(ArrayList<A> list)
    {
        return VUtility.cast(list.clone());
    }
    
    private static boolean wasShutDown = false;
    
    public static void shutdown()
    {
        VUtility.wasShutDown = true;
        VUtility.setShutdown(true);
    }
    
    public static void unShutdown()
    {
        if(VUtility.wasShutDown)
        {
            VUtility.wasShutDown = false;
            VUtility.setShutdown(false);
        }
    }
    
    public static void setShutdown(boolean value)
    {
        VConfigHelper.setValueAndSave(VConfigHelper.commonConfig, "shutdown", value);
        VConfiguration.shutdown = value;
    }
    
    public static boolean isShutdown()
    {
        return VConfiguration.shutdown;
    }
    
    public static void openGuiForClient(PrintProvider helper)
    {
        Visibilis.proxy.openGuiPrint(helper);
    }
}
