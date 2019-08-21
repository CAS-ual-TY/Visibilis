package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.NodeNumber1to1;

public class NodeRound extends NodeNumber1to1
{
    @Override
    protected Number calculate(Number in1)
    {
        if (in1 instanceof Integer)
        {
            return in1.intValue(); // Dont round if already int
        }
        
        return Math.round(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "round";
    }
}