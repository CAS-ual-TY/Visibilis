package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatP2;

public class NodeModulo extends NodeFloatP2
{
    public NodeModulo(NodeType<?> type)//TODO low: Check lang file for translation of 1st input. Find out actual name of said input
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
        return in1.floatValue() % in2.floatValue();
    }
}
