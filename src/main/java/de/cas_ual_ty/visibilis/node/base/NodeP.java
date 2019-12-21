package de.cas_ual_ty.visibilis.node.base;

import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;

public abstract class NodeP extends NodeX
{
    /*
     * When expanding, both Outputs and Inputs are added or removed.
     * Inputs affect only a single Output dedicated only to them.
     * The other Inputs affect all Outputs.
     */
    
    public NodeP()
    {
        super();
    }
    
    public abstract Output createDynamicOutput();
    
    @Override
    public void expand()
    {
        this.addOutput(this.createDynamicOutput(), this.getOutputAmt());
        super.expand();
    }
    
    @Override
    public void shrink()
    {
        this.removeOutput(this.getOutputAmt() - 1);
        super.shrink();
    }
    
    @Override
    public String getFieldName(NodeField field)
    {
        return super.getFieldName(field) + (field.isOutput() || (field.isInput() && field.getId() < (this.getInputAmt() - this.getExtraInAmt())) ? " " + (field.getId() + 1) : "");
    }
}
