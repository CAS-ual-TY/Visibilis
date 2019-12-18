package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberP1;

public class NodeTangent extends NodeNumberP1
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
