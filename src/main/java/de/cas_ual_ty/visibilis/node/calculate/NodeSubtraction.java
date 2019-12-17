package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber2to1P;

public class NodeSubtraction extends NodeNumber2to1P
{
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() - in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "subtraction";
    }
}
