package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeTangent extends NodeNumber1to1
{
    public NodeTangent()
    {
        super();
    }
    
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.tan(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "func_tan";
    }
}