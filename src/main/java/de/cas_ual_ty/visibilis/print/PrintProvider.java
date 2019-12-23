package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public abstract class PrintProvider
{
    /*
     * Provides control and customization for the default implementation in the visibilis.print.impl package.
     * If you need access to the screen from here, remember that you can also do that with: Minecraft.getInstance().currentScreen
     */
    
    public UndoList undoList;
    
    public PrintProvider()
    {
        this.undoList = new UndoList();
    }
    
    public Print getPrint()
    {
        return this.undoList.getCurrent();
    }
    
    /**
     * Get the current available nodes for the side view. THIS IS NOT NULLABLE
     */
    public abstract ArrayList<Node> getAvailableNodes();
    
    /**
     * Called when the gui is opened (beginning of constructor {@link Screen#Screen(ITextComponent)})
     */
    public abstract void init();
    
    /**
     * Called when the gui is opened (end of constructor {@link Screen#Screen(ITextComponent)})
     */
    public abstract void onGuiOpen();
    
    /**
     * Called when the opened gui is closed (end of {@link Screen#onClose()}
     */
    public abstract void onGuiClose();
    
    /**
     * Is the user permitted to delete the given node? Used in {@link #getActionsForNode(Screen, Node)}.
     */
    public boolean canDeleteNode(Node node)
    {
        return true;
    }
    
    /**
     * Return all possible node actions for the given nodes. Adds a deletion option by default.
     * @see NodeAction
     * @see #canDeleteNode(Screen, Node)
     */
    public ArrayList<NodeAction> getActionsForNode(Node node)
    {
        ArrayList<NodeAction> list = node.getActions();
        
        if (this.canDeleteNode(node))
        {
            list.add(new NodeAction(node, NodeAction.LANG_DELETE)
            {
                
                @Override
                public boolean clicked()
                {
                    PrintProvider.this.getPrint().removeNode(this.node);
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
