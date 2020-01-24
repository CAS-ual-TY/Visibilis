package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatXP2;

public class NodeAddition extends NodeFloatXP2
{
    public NodeAddition(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float calculate(Float[] inputs)
    {
        float value = 0;
        
        for(Float in : inputs)
        {
            value += in.floatValue();
        }
        
        return value;
    }
    
    @Override
    protected Float calculate(Float in1, Float in2)
    {
        return in1.floatValue() + in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "addition";
    }
}
