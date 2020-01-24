package de.cas_ual_ty.visibilis.node.compare;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatCompare;

public class NodeFloatGreater extends NodeFloatCompare
{
    public NodeFloatGreater(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Boolean compare(Float input, Float in2)
    {
        return input.floatValue() > in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "greater_than";
    }
}
