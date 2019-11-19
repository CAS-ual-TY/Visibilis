package de.cas_ual_ty.visibilis.print.impl;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public interface IPrintProvider
{
    /*
     * Provides control and customization for the default implementation in the visibilis.print.impl package.
     */
    
    public Print getPrint(Screen gui);
    
    /**
     * Get the current available nodes for the side view. THIS IS NOT NULLABLE
     */
    public ArrayList<Node> getAvailableNodes(Screen gui);
    
    /**
     * Called when the gui is opened (end of constructor {@link Screen#Screen(ITextComponent)})
     */
    public void onGuiOpen(Screen gui);
    
    /**
     * Called when the opened gui is closed (end of {@link Screen#onClose()}
     */
    public void onGuiClose(Screen gui);
    
    /**
     * Is the user permitted to delete the given node? Used in {@link #getActionsForNode(Screen, Node)}.
     */
    public default boolean canDeleteNode(Screen gui, Node node)
    {
        return true;
    }
    
    /**
     * Return all possible node actions for the given nodes. Adds a deletion option by default.
     * @see NodeAction
     * @see #canDeleteNode(Screen, Node)
     */
    public default ArrayList<NodeAction> getActionsForNode(Screen gui, Node node)
    {
        ArrayList<NodeAction> list = node.getActions();
        
        if (this.canDeleteNode(gui, node))
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
