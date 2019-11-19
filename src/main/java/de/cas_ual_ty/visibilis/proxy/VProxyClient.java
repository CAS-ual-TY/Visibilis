package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.handler.VEventHandlerClient;
import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.impl.GuiPrint;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
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
    public void openGuiPrint(IPrintProvider helper)
    {
        //        Minecraft.getMinecraft().displayGuiScreen(new GuiPrintOld(helper));
        Minecraft.getInstance().displayGuiScreen(new GuiPrint(new StringTextComponent("Print"), helper));
    }
}
