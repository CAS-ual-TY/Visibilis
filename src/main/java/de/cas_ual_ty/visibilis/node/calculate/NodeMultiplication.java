package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumberXP2;

public class NodeMultiplication extends NodeNumberXP2
{
    @Override
    protected Number calculate(Number[] inputs)
    {
        float value = 1;
        
        for (Number in : inputs)
        {
            value *= in.floatValue();
        }
        
        return value;
    }
    
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() * in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "multiplication";
    }
}
