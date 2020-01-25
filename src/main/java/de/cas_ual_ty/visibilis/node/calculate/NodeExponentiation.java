package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP2;

public class NodeExponentiation extends NodeFloatP2
{
    public NodeExponentiation(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1, Float in2)
    {
        return (float)Math.pow(in1.floatValue(), in2.floatValue());
    }
}
