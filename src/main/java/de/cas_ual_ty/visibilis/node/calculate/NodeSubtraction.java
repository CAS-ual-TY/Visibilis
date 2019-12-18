package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberP2;

public class NodeSubtraction extends NodeNumberP2
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
