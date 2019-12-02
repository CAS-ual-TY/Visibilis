package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber2Xto1;

public class NodeSubtraction extends NodeNumber2Xto1
{
    @Override
    protected Number calculate(Number[] inputs)
    {
        float value = inputs[0].floatValue();
        
        for (Number in : inputs)
        {
            value -= in.floatValue();
        }
        
        return value;
    }
    
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() - in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "subtraction";
    }
}
