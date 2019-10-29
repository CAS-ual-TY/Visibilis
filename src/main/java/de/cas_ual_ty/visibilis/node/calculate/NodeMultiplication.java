package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber2Xto1;

public class NodeMultiplication extends NodeNumber2Xto1
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
    public String getID()
    {
        return "multiplication";
    }
}
