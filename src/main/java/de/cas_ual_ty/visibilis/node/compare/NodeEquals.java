package de.cas_ual_ty.visibilis.node.compare;

import de.cas_ual_ty.visibilis.node.general.NodeNumberBoolean2to1;

public class NodeEquals extends NodeNumberBoolean2to1
{
    @Override
    protected Boolean calculate(Number in1, Number in2)
    {
        return in1.equals(in2);
    }
    
    @Override
    public String getID()
    {
        return "equals";
    }
}
