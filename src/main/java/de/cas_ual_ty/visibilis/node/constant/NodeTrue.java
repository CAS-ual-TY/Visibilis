package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeBoolean0to1;

public class NodeTrue extends NodeBoolean0to1
{
    public NodeTrue()
    {
        super();
    }
    
    @Override
    protected boolean getValue()
    {
        return true;
    }
    
    @Override
    public String getID()
    {
        return "true";
    }
}
