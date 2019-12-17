package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber2to1PX;

public class NodeAddition extends NodeNumber2to1PX
{
    @Override
    protected Number calculate(Number[] inputs)
    {
        float value = 0;
        
        for (Number in : inputs)
        {
            value += in.floatValue();
        }
        
        return value;
    }
    
    @Override
    protected Number calculate(Number in1, Number in2)
    {
        return in1.floatValue() + in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "addition";
    }
}
