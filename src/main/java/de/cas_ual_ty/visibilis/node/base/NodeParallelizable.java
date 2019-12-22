package de.cas_ual_ty.visibilis.node.base;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.NodeAction;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeParallelizable extends NodeExpandable
{
    /*
     * This node can be in normal or parallel mode.
     * When expanding, either only Inputs are added (normal), or Outputs as well (parallel).
     * In parallel mode, Inputs affect only a single Output dedicated only to them;
     * in normal mode, all Inputs affect all Outputs.
     * You can always switch between normal and parallel mode.
     */
    
    public static final String KEY_PARALLELIZATION = "parallelization_status";
    
    public boolean parallelized;
    
    public NodeParallelizable()
    {
        super();
        this.parallelized = false;
    }
    
    public void actionParallelize()
    {
        this.parallelized = true;
        this.parallelize();
    }
    
    public void actionUnparallelize()
    {
        this.parallelized = false;
        this.unparallelize();
    }
    
    public abstract void parallelize();
    
    public abstract void unparallelize();
    
    public boolean canParallelize()
    {
        return !this.parallelized;
    }
    
    public boolean canUnparallelize()
    {
        return this.parallelized;
    }
    
    @Override
    public ArrayList<NodeAction> getActions()
    {
        ArrayList<NodeAction> list = super.getActions();
        
        if (this.canParallelize())
        {
            list.add(this.createActionParallelize());
        }
        
        if (this.canUnparallelize())
        {
            list.add(this.createActionUnparallelize());
        }
        
        return list;
    }
    
    public NodeAction createActionParallelize()
    {
        return new NodeAction(this, NodeAction.LANG_PARALLELIZE)
        {
            @Override
            public boolean clicked()
            {
                NodeParallelizable.this.actionParallelize();
                return true;
            }
        };
    }
    
    public NodeAction createActionUnparallelize()
    {
        return new NodeAction(this, NodeAction.LANG_UNPARALLELIZE)
        {
            @Override
            public boolean clicked()
            {
                NodeParallelizable.this.actionUnparallelize();
                return true;
            }
        };
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        super.readNodeFromNBT(nbt);
        boolean parallelized = nbt.getBoolean(NodeParallelizable.KEY_PARALLELIZATION);
        
        if (parallelized)
        {
            this.actionParallelize();
        }
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        nbt.putBoolean(NodeParallelizable.KEY_PARALLELIZATION, this.parallelized);
    }
}
