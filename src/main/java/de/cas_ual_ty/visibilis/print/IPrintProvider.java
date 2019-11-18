package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
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
    
    public default boolean canDeleteNode(Screen gui, Node node)
    {
        return true;
    }
    
    public default ArrayList<NodeAction> getActionsForNode(Screen gui, Node node)
    {
        ArrayList<NodeAction> list = node.getActions();
        
        if(this.canDeleteNode(gui, node))
        {
            list.add(new NodeAction(node, NodeAction.LANG_DELETE)
            {
                
                @Override
                public boolean clicked()
                {
                    IPrintProvider.this.getPrint(gui).removeNode(this.node);
                    return true;
                }
            });
        }
        
        return list;
    }
}
