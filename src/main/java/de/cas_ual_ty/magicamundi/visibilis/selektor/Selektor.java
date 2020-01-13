package de.cas_ual_ty.magicamundi.visibilis.selektor;

import de.cas_ual_ty.magicamundi.visibilis.MMDataType;
import de.cas_ual_ty.magicamundi.visibilis.target.TargetsList;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VUtility;

public abstract class Selektor extends Node
{
    public final Output<Object> outExec;
    public final Output<TargetsList> outTargetsList;
    
    public final TargetsList targetsList;
    
    public Selektor()
    {
        super();
        this.addOutput(this.outExec = new Output<>(this, DataType.EXEC, "exec"));
        this.addOutput(this.outTargetsList = new Output<>(this, MMDataType.TARGETS_LIST, "targets_list"));
        this.targetsList = new TargetsList();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.targetsList.clear();
        return this.findTargets(this.targetsList);
    }
    
    /**
     * Find/select all targets and add them to the given TargetsList.
     * 
     * @param list
     *            The list to add all found/selected targets to.
     * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
     */
    public abstract boolean findTargets(TargetsList list);
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        if(out == this.outTargetsList)
        {
            return VUtility.cast(this.targetsList);
        }
        
        return null;
    }
}
