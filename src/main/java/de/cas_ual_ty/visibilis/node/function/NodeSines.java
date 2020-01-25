package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeSines extends NodeFloatP
{
    public NodeSines(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1)
    {
        return (float)Math.sin(in1.floatValue());
    }
}
