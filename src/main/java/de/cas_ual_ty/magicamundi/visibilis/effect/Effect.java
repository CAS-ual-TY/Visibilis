package de.cas_ual_ty.magicamundi.visibilis.effect;

import de.cas_ual_ty.magicamundi.visibilis.MMDataType;
import de.cas_ual_ty.magicamundi.visibilis.target.TargetsList;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class Effect extends Node implements INodeExec
{
    public final Output<Object> outExec;
    public final Input<Object> inExec;
    public final Input<TargetsList> inTargetsList;
    
    public Effect()
    {
        super();
        this.addOutput(this.outExec = new Output<>(this, DataType.EXEC, "exec"));
        this.addInput(this.inExec = new Input<>(this, DataType.EXEC, "exec"));
        this.addInput(this.inTargetsList = new Input<>(this, MMDataType.TARGETS_LIST, "targets_list"));
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
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
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.outExec : null;
    }
}
