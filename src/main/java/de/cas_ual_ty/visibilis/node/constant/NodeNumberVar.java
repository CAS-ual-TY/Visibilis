package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberP1;

public class NodeNumberVar extends NodeNumberP1
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
