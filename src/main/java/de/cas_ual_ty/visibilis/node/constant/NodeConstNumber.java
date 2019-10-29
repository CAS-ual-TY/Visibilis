package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeConstNumber extends NodeNumber1to1
{
    public NodeConstNumber()
    {
        super();
    }
    
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
