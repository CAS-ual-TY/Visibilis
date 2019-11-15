package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.client.gui.screen.Screen;

public interface IPrintProvider
{
    public Print getPrint(Screen gui);
    
    /**
     * Get the current available nodes for the side view
     */
    @Nullable //TODO maybe dont make this nullable
    public ArrayList<Node> getAvailableNodes(Screen gui);
    
    /**
     * Called at the end of {@link GuiPrintOld#GuiPrint(IGuiPrintHelper)} (constructor)
     */
    public void onGuiOpen(Screen gui);
    
    /**
     * Called at the end of {@link GuiPrintOld#onGuiClosed()}
     */
    public void onGuiClose(Screen gui);
}
