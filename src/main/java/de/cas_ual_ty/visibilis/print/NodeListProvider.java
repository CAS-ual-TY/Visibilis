package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.client.gui.screen.Screen;

public abstract class NodeListProvider
{
    public NodeListProvider()
    {
    }
    
    /**
     * Get the current available nodes for the side view. THIS IS NOT NULLABLE
     */
    public abstract ArrayList<Node> getAvailableNodes();
    
    /**
     * Called when a node from {@link #getAvailableNodes()} is added to the print
     * @param node The newly added node
     */
    public void onNodeAdded(Node node)
    {
    }
    
    /**
     * Called when a node is removed from the print
     * @param node The removed node
     */
    public void onNodeRemoved(Node node)
    {
    }
    
    /**
     * Is the user permitted to delete the given node? Used in {@link #getActionsForNode(Screen, Node)}.
     */
    public boolean canDeleteNode(Node node)
    {
        return true;
    }
}
