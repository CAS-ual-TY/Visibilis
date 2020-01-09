package de.cas_ual_ty.visibilis.node.base;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeExpandable extends Node
{
    /*
     * When expanding, only Inputs are added or removed.
     * All Inputs affect all Outputs.
     */
    
    public static final String KEY_EXPANSION = "expansion_status";
    
    public int expansion;
    
    public NodeExpandable()
    {
        super();
        this.expansion = 0;
    }
    
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
    
    public abstract void expand();
    
    public abstract void shrink();
    
    @Override
    public ArrayList<NodeAction> getActions()
    {
        ArrayList<NodeAction> list = super.getActions();
        
        if(this.canExpand())
        {
            list.add(this.createActionExpand());
        }
        
        if(this.canShrink())
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
                NodeExpandable.this.actionExpand();
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
                NodeExpandable.this.actionShrink();
                return true;
            }
        };
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        int expansion = nbt.getInt(NodeExpandable.KEY_EXPANSION);
        
        for(int i = 0; i < expansion; ++i)
        {
            this.actionExpand();
        }
        
        super.readNodeFromNBT(nbt);
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        nbt.putInt(NodeExpandable.KEY_EXPANSION, this.expansion);
        
        super.writeNodeToNBT(nbt);
    }
}
