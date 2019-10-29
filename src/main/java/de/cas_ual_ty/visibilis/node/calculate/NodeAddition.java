package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber2Xto1;

public class NodeAddition extends NodeNumber2Xto1
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
    public String getID()
    {
        return "addition";
    }
}
