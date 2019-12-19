package de.cas_ual_ty.visibilis.node.general;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeXP extends NodeX
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
    
    public NodeXP()
    {
        super();
        this.parallelized = false;
    }
    
    public abstract Output createDynamicOutput();
    
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
    
    @Override
    public void expand()
    {
        if (this.parallelized)
        {
            this.addOutput(this.createDynamicOutput());
        }
        
        super.expand();
    }
    
    @Override
    public void shrink()
    {
        if (this.parallelized)
        {
            this.removeOutput(this.getOutputAmt() - 1);
        }
        
        super.shrink();
    }
    
    public void parallelize()
    {
        for (int i = this.getOutputAmt(); i < this.getInputAmt() - this.getExtraInAmt(); ++i)
        {
            this.addOutput(this.createDynamicOutput());
        }
    }
    
    public void unparallelize()
    {
        for (int i = this.getOutputAmt() - 1; i > 0; --i)
        {
            this.removeOutput(this.getOutput(i));
        }
    }
    
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
                NodeXP.this.actionParallelize();
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
                NodeXP.this.actionUnparallelize();
                return true;
            }
        };
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        super.readNodeFromNBT(nbt);
        boolean parallelized = nbt.getBoolean(NodeXP.KEY_PARALLELIZATION);
        
        if (parallelized)
        {
            this.actionParallelize();
        }
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        nbt.putBoolean(NodeXP.KEY_PARALLELIZATION, this.parallelized);
    }
}
