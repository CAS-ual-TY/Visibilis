package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP2;

public class NodeRoot extends NodeFloatP2
{
    @Override
    protected boolean canCalculate(Float in1, Float in2)
    {
        return in2.floatValue() != 0;
    }
    
    @Override
    protected Float calculate(Float in1, Float in2)
    {
        return (float) Math.pow(Math.E, Math.log(in1.floatValue()) / in2.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "root";
    }
}
