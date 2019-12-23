package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeLogarithm10 extends NodeFloatP
{
    @Override
    protected Float calculate(Float in1)
    {
        return (float) Math.log10(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "logarithm_10";
    }
}
