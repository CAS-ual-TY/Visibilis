package de.cas_ual_ty.visibilis.node.compare;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatCompare;

public class NodeFloatLess extends NodeFloatCompare
{
    public NodeFloatLess()
    {
        super();
    }
    
    @Override
    protected Boolean compare(Float input, Float in2)
    {
        return input.floatValue() < in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "less_than";
    }
}
