package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber2to1P;

public class NodeExponentiation extends NodeNumber2to1P
{
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return (float) Math.pow(in1.floatValue(), in2.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "exponentiation";
    }
}
