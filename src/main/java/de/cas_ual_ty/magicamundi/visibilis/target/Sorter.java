package de.cas_ual_ty.magicamundi.visibilis.target;

import de.cas_ual_ty.magicamundi.visibilis.MMDataType;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class Sorter extends Node implements INodeExec
{
    public final Output outExec;
    public final Output<TargetsList> outTargetsList1;
    public final Output<TargetsList> outTargetsList2;
    public final Input inExec;
    public final Input<TargetsList> inTargetsList;
    
    public TargetsList targetsList1;
    public final TargetsList targetsList2;
    
    public Sorter(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec = new Output(this, DataType.EXEC, "exec");
        this.outTargetsList1 = new Output<>(this, MMDataType.TARGETS_LIST, "targets_list");
        this.outTargetsList2 = new Output<>(this, MMDataType.TARGETS_LIST, "targets_list");
        this.inExec = new Input(this, DataType.EXEC, "exec");
        this.inTargetsList = new Input<>(this, MMDataType.TARGETS_LIST, "targets_list");
        this.targetsList2 = new TargetsList();
    }
    
    public Sorter(int inputAmt)
    {
        this(3, inputAmt);
    }
    
    public Sorter()
    {
        this(2);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.targetsList2.clear();
        this.targetsList1 = this.inTargetsList.getValue().clone();
        return this.sortOut(this.targetsList1, this.targetsList2);
    }
    
    /**
     * Sort out the targets from one list and add them to the other.
     * 
     * @param list1
     *            The TargetsList instance containing all Target instances to remove false targets from.
     * @param list2
     *            The TargetsList instance to add all removed targets to.
     * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
     */
    public abstract boolean sortOut(TargetsList list1, TargetsList list2);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.outTargetsList1.getId())
        {
            return (B) this.targetsList1;
        }
        else if (index == this.outTargetsList2.getId())
        {
            return (B) this.targetsList2;
        }
        
        return null;
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.outExec : null;
    }
}
