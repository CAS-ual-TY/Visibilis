package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberP;

public class NodeTangent extends NodeNumberP
{
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.tan(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "tangent";
    }
}
