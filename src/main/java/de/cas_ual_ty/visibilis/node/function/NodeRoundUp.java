package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberP;

public class NodeRoundUp extends NodeNumberP
{
    @Override
    protected Number calculate(Number in1)
    {
        if (in1 instanceof Integer)
        {
            return in1.intValue(); // Dont round if already int
        }
        
        return Math.ceil(in1.floatValue());
    }
    
    @Override
    public String getID()
    {
        return "round_up";
    }
}
