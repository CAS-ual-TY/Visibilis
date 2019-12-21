package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberP1;

public class NodeLogarithm10 extends NodeNumberP1
{
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.log10(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "logarithm_10";
    }
}
