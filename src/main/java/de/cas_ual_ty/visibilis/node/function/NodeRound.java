package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeRound extends NodeFloatP
{
    public NodeRound(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1)
    {
        return (float)Math.round(in1.floatValue());
    }
}
