package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.number.NodeNumber1to1P;

public class NodeRound extends NodeNumber1to1P
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
