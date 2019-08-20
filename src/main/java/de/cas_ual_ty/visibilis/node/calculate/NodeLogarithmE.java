package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeLogarithmE extends NodeNumber1to1
{
    public NodeLogarithmE()
    {
        super();
    }
    
    @Override
    protected Number calculate(Number in1)
    {
        return (float) Math.log(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "logarithm_e";
    }
}
