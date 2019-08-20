package de.cas_ual_ty.magicamundi.visibilis.effect;

import de.cas_ual_ty.magicamundi.visibilis.MMDataType;
import de.cas_ual_ty.magicamundi.visibilis.target.TargetsList;
import de.cas_ual_ty.visibilis.datatype.VDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class Effect extends NodeExec
{
    public final Output outExec;
    public final Input inExec;
    public final Input<TargetsList> inTargetsList;
    
    public Effect(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec = new Output(0, this, VDataType.EXEC, "exec");
        this.inExec = new Input(0, this, VDataType.EXEC, "exec");
        this.inTargetsList = new Input<TargetsList>(1, this, MMDataType.TARGETS_LIST, "targets_list");
    }
    
    public Effect(int inputAmt)
    {
        this(1, inputAmt);
    }
    
    public Effect()
    {
        this(2);
    }
    
    @Override
    public boolean doCalculate()
    {
        return this.applyEffect(this.inTargetsList.getValue());
    }
    
    /**
     * Apply the effect to all given targets.
     * 
     * @param list
     *            The list of targets to apply the effect to.
     * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
     */
    public abstract boolean applyEffect(TargetsList list);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return null;
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.outExec : null;
    }
}
