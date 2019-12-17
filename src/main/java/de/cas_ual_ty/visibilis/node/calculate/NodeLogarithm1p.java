package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber1to1P;

public class NodeLogarithm1p extends NodeNumber1to1P
{
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
