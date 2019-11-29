package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.print.impl.GuiPrint;
import de.cas_ual_ty.visibilis.print.impl.PrintProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

public class VProxyClient implements IVSidedProxy
{
    @Override
    public void preInit()
    {
        
    }
    
    @Override
    public void init()
    {
    }
    
    @Override
    public void openGuiPrint(PrintProvider helper)
    {
        //        Minecraft.getMinecraft().displayGuiScreen(new GuiPrintOld(helper));
        Minecraft.getInstance().displayGuiScreen(new GuiPrint(new StringTextComponent("Print"), helper));
    }
}
