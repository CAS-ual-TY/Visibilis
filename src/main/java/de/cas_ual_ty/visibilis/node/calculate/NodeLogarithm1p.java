package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeLogarithm1p extends NodeNumber1to1
{
    public NodeLogarithm1p()
    {
        super();
    }
    
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.log1p(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "logarithm_1p";
    }
}
