package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP2;

public class NodeDivision extends NodeFloatP2
{
    public NodeDivision(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected boolean canCalculate(Float in1, Float in2)
    {
        return in2.floatValue() != 0;
    }
    
    @Override
    protected Float calculate(Float in1, Float in2)
    {
        return in1.floatValue() / in2.floatValue();
    }
}
