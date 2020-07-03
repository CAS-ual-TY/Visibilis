package de.cas_ual_ty.visibilis.print.provider;

import java.util.List;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.client.gui.screen.Screen;

public abstract class NodeListProvider
{
    public NodeListProvider()
    {
    }
    
    public abstract List<NodeType<?>> getAvailableNodeTypes();
    
    /**
     * Get the current available nodes for the side view. THIS IS NOT NULLABLE
     */
    public abstract List<Node> getAvailableNodes();
    
    /**
     * Called when a node from {@link #getAvailableNodes()} is added to the print
     * @param node The newly added node
     */
    public void onNodeAdded(Node node)
    {
        if(node.type.isEvent())
        {
            this.getAvailableNodes().remove(node);
        }
        else
        {
            int index = this.getAvailableNodes().indexOf(node);
            this.getAvailableNodes().remove(index);
            this.getAvailableNodes().add(index, node.type.instantiate());
        }
    }
    
    /**
     * Called when a node is removed from the print
     * @param node The removed node
     */
    public void onNodeRemoved(Node node)
    {
        if(node.type.isEvent())
        {
            int index = this.getAvailableNodeTypes().indexOf(node.type);
            
            if(index != -1)
            {
                int indexType = -1;
                
                for(int indexList = 0; indexList < this.getAvailableNodes().size(); ++indexList)
                {
                    indexType = this.getAvailableNodeTypes().indexOf(this.getAvailableNodes().get(indexList).type);
                    
                    if(indexType > index)
                    {
                        this.getAvailableNodes().add(indexList, node);
                        return;
                    }
                }
            }
            
            this.getAvailableNodes().add(node);
        }
    }
    
    public void onOpen(Print print)
    {
        // Remove all already existing event nodes so they cant be added twice
        
        Node f;
        for(Node n : print.getEvents())
        {
            f = null;
            for(Node n1 : this.getAvailableNodes())
            {
                if(n.type == n1.type)
                {
                    f = n1;
                    break;
                }
            }
            
            if(f != null)
            {
                this.getAvailableNodes().remove(f);
            }
        }
    }
    
    /**
     * Is the user permitted to delete the given node? Used in {@link #getActionsForNode(Screen, Node)}.
     */
    public boolean canDeleteNode(Node node)
    {
        return node.type != VNodeTypes.FUNCTION_START && node.type != VNodeTypes.FUNCTION_END;
    }
    
    public boolean validate(Print p)
    {
        return VUtility.validate(p, this.getAvailableNodeTypes());
    }
}
