package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeBoolean1to1;

public class NodeConstBoolean extends NodeBoolean1to1
{
    public NodeConstBoolean()
    {
        
    }
    
    @Override
    protected boolean calculate(boolean in1)
    {
        return in1;
    }
    
    @Override
    public String getID()
    {
        return "const_boolean";
    }
}
