package de.cas_ual_ty.visibilis.node.general;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.node.field.Input;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeX extends Node
{
    /*
     * When expanding, only Inputs are added or removed.
     * All Inputs affect all Outputs.
     */
    
    public static final String KEY_EXPANSION = "expansion_status";
    
    public int expansion;
    
    public NodeX()
    {
        super();
        this.expansion = 0;
    }
    
    public abstract Input createDynamicInput();
    
    public void actionExpand()
    {
        ++this.expansion;
        this.expand();
    }
    
    public void actionShrink()
    {
        --this.expansion;
        this.shrink();
    }
    
    public boolean canExpand()
    {
        return true;
    }
    
    public boolean canShrink()
    {
        return this.expansion > 0;
    }
    
    public void expand()
    {
        this.addInput(this.createDynamicInput());
    }
    
    public void shrink()
    {
        this.removeInput(this.getInput(this.getInputAmt() - 1));
    }
    
    @Override
    public ArrayList<NodeAction> getActions()
    {
        ArrayList<NodeAction> list = super.getActions();
        
        if (this.canExpand())
        {
            list.add(this.createActionExpand());
        }
        
        if (this.canShrink())
        {
            list.add(this.createActionShrink());
        }
        
        return list;
    }
    
    public NodeAction createActionExpand()
    {
        return new NodeAction(this, NodeAction.LANG_EXPAND)
        {
            @Override
            public boolean clicked()
            {
                NodeX.this.actionExpand();
                return true;
            }
        };
    }
    
    public NodeAction createActionShrink()
    {
        return new NodeAction(this, NodeAction.LANG_SHRINK)
        {
            @Override
            public boolean clicked()
            {
                NodeX.this.actionShrink();
                return true;
            }
        };
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        super.readNodeFromNBT(nbt);
        int expansion = nbt.getInt(NodeX.KEY_EXPANSION);
        
        for (int i = 0; i < expansion; ++i)
        {
            this.actionExpand();
        }
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        nbt.putInt(NodeX.KEY_EXPANSION, this.expansion);
    }
}
