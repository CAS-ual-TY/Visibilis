package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.handler.VEventHandlerClient;
import de.cas_ual_ty.visibilis.print.GuiPrint;
import de.cas_ual_ty.visibilis.print.IPrintHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class VProxyClient implements IVSidedProxy
{
    public static VEventHandlerClient eventHandlerClient;
    
    @Override
    public void preInit()
    {
        
    }
    
    @Override
    public void init()
    {
        MinecraftForge.EVENT_BUS.register((VProxyClient.eventHandlerClient = new VEventHandlerClient()));
    }
    
    @Override
    public void openGuiPrint(IPrintHelper helper)
    {
        //        Minecraft.getMinecraft().displayGuiScreen(new GuiPrintOld(helper));
        Minecraft.getMinecraft().displayGuiScreen(new GuiPrint(helper));
    }
}
