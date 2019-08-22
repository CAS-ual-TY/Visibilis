package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;

public interface IPrintHelper
{
    public Print getPrint(GuiPrint gui);
    
    /**
     * Get the current available nodes for the side view
     */
    public ArrayList<Node> getAvailableNodes(GuiPrint gui);
    
    /**
     * Called at the end of {@link GuiPrint#GuiPrint(IGuiPrintHelper)} (constructor)
     */
    public void onGuiOpen(GuiPrint gui);
    
    /**
     * Called at the end of {@link GuiPrint#initGui()}
     */
    public void onGuiInit(GuiPrint gui);
    
    /**
     * Called at the end of {@link GuiPrint#onGuiClosed()}
     */
    public void onGuiClose(GuiPrint gui);
}
