package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberP1;

public class NodeLogarithm1p extends NodeNumberP1
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
