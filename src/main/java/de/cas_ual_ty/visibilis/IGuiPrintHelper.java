package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;

public interface IGuiPrintHelper
{
    public Print getActivePrint(GuiPrint gui);
    
    public ArrayList<Node> getAvailableNodes(GuiPrint gui);
    
    public void onGuiOpen(GuiPrint gui);
    public void onGuiInit(GuiPrint gui);
    public void onGuiClose(GuiPrint gui);
}
