package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeCosines extends NodeNumber1to1
{
    public NodeCosines()
    {
        super();
    }
    
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.cos(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "cosines";
    }
}
