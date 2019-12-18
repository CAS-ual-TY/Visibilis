package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberP2;

public class NodeRoot extends NodeNumberP2
{
    @Override
    protected boolean canCalculate(Number in1, Number in2)
    {
        return in2.floatValue() != 0;
    }
    
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return (float) Math.pow(Math.E, Math.log(in1.floatValue()) / in2.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "root";
    }
}
