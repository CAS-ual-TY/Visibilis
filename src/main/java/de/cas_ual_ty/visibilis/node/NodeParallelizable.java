package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;

import net.minecraft.nbt.CompoundNBT;

public abstract class NodeParallelizable extends NodeExpandable
{
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
        ArrayList<NodeAction> list =  super.getActions();
        
        if(this.canParallelize())
        {
            list.add(this.createActionParallelize());
        }
        
        if(this.canUnparallelize())
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
        boolean parallelized = nbt.getBoolean(KEY_PARALLELIZATION);
        
        if(parallelized)
        {
            this.actionParallelize();
        }
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        nbt.putBoolean(KEY_PARALLELIZATION, this.parallelized);
    }
}
