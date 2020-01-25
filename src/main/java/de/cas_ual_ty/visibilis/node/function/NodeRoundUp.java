package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeRoundUp extends NodeFloatP
{
    public NodeRoundUp(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1)
    {
        return (float)Math.ceil(in1.floatValue());
    }
}
