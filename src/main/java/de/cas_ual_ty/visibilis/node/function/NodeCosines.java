package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeCosines extends NodeFloatP
{
    public NodeCosines(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1)
    {
        return (float)Math.cos(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "cosines";
    }
}
