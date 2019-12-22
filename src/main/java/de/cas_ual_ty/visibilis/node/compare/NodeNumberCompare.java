package de.cas_ual_ty.visibilis.node.compare;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberE;

public class NodeNumberCompare extends NodeNumberE
{
    public NodeNumberCompare()
    {
        super();
    }
    
    @Override
    protected Boolean compare(Number input, Number in2)
    {
        return input.floatValue() == in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "equals";
    }
}
