package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeParallel extends NodeExpandable
{
    /*
     * When expanding, both Outputs and Inputs are added or removed.
     * Some Inputs affect only a single Output dedicated only to them.
     * The other Inputs affect all Outputs.
     */
    
    public Number[] values;
    
    public NodeParallel()
    {
        super();
    }
    
    public abstract Output createDynamicOutput();
    
    @Override
    public void expand()
    {
        this.addOutput(this.createDynamicOutput());
        super.expand();
    }
    
    @Override
    public void shrink()
    {
        this.removeOutput(this.getOutput(this.getOutputAmt() - 1));
        super.shrink();
    }
    
    @Override
    public String getFieldName(NodeField field)
    {
        return super.getFieldName(field) + " " + (field.getId() + 1);
    }
}
