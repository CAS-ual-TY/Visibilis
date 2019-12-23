package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeSines extends NodeFloatP
{
    @Override
    protected Float calculate(Float in1)
    {
        return (float) Math.sin(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "sines";
    }
}
