package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber1to1P;

public class NodeConstNumber extends NodeNumber1to1P
{
    @Override
    protected Number calculate(Number in1)
    {
        return (float) in1.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "const_number";
    }
}
