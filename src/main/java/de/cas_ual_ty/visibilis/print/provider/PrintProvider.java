package de.cas_ual_ty.visibilis.print.provider;

import java.util.List;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.UndoList;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public abstract class PrintProvider
{
    /*
     * Provides control and customization for the default implementations.
     * If you need access to the screen from here, remember that you can also do that with: Minecraft.getInstance().currentScreen
     */
    
    protected NodeListProvider nodeListProvider;
    protected UndoList undoList;
    
    public PrintProvider(NodeListProvider nodeListProvider)
    {
        this.nodeListProvider = nodeListProvider;
        this.undoList = new UndoList();
    }
    
    public Print getPrint()
    {
        return this.undoList.getCurrent();
    }
    
    /**
     * Get the current available nodes for the side view. THIS IS NOT NULLABLE
     */
    public final List<Node> getAvailableNodes()
    {
        return this.nodeListProvider.getAvailableNodes();
    }
    
    /**
     * Called when a node from {@link #getAvailableNodes()} is added to the print
     * @param node The newly added node
     */
    public void onNodeAdded(Node node)
    {
        this.nodeListProvider.onNodeAdded(node);
    }
    
    /**
     * Called when a node is removed from the print
     * @param node The removed node
     */
    public void onNodeRemoved(Node node)
    {
        this.nodeListProvider.onNodeRemoved(node);
    }
    
    /**
     * Called when the gui is opened (beginning of constructor {@link Screen#Screen(ITextComponent)})
     */
    public void init()
    {
        
    }
    
    /**
     * Called when the gui is opened (end of constructor {@link Screen#Screen(ITextComponent)})
     */
    public void onGuiOpen()
    {
        this.nodeListProvider.onOpen(this.getPrint());
    }
    
    /**
     * Called when the opened gui is closed (end of {@link Screen#onClose()}
     */
    public void onGuiClose()
    {
        
    }
    
    /**
     * Is the user permitted to delete the given node? Used in {@link #getActionsForNode(Screen, Node)}.
     */
    public boolean canDeleteNode(Node node)
    {
        return true;
    }
    
    public boolean canCloneNode(Node node)
    {
        return node.type != VNodeTypes.FUNCTION_START && node.type != VNodeTypes.FUNCTION_END;
    }
    
    /**
     * Return all possible node actions for the given nodes. Adds a deletion option by default.
     * @see NodeAction
     * @see #canDeleteNode(Screen, Node)
     */
    public List<NodeAction> getActionsForNode(Node node)
    {
        List<NodeAction> list = node.getActions();
        
        if(this.canCloneNode(node))
        {
            list.add(new NodeAction(node, NodeAction.LANG_CLONE)
            {
                @Override
                public boolean clicked()
                {
                    int x = this.node.getPosX() + 4;
                    int y = this.node.getPosY() + 4;
                    PrintProvider.this.getPrint().addNode(this.node.clone().setPosition(x, y));
                    return true;
                }
            });
        }
        
        if(this.canDeleteNode(node))
        {
            list.add(new NodeAction(node, NodeAction.LANG_DELETE)
            {
                @Override
                public boolean clicked()
                {
                    PrintProvider.this.getPrint().removeNode(this.node);
                    PrintProvider.this.onNodeRemoved(this.node);
                    return true;
                }
            });
        }
        
        return list;
    }
    
    public void saveChange()
    {
        this.undoList.saveChange(this.getPrint());
    }
    
    public void undo()
    {
        this.undoList.undo();
    }
    
    public void redo()
    {
        this.undoList.redo();
    }
    
    public boolean canUndo()
    {
        return this.undoList.canUndo();
    }
    
    public boolean canRedo()
    {
        return this.undoList.canRedo();
    }
}
