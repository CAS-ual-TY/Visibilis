package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeSines extends NodeNumber1to1
{
    public NodeSines()
    {
        super();
    }
    
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.sin(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "func_sin";
    }
}