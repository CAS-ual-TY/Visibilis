package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP;

public class NodeLogarithmE extends NodeFloatP
{
    public NodeLogarithmE(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float in1)
    {
        return (float)Math.log(in1.floatValue());
    }
}
