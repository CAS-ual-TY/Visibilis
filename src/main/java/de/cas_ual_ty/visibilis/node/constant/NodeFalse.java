package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.general.NodeBoolean0to1;

public class NodeFalse extends NodeBoolean0to1
{
    public NodeFalse()
    {
        super();
    }
    
    @Override
    protected boolean getValue()
    {
        return false;
    }
    
    @Override
    public String getID()
    {
        return "false";
    }
}
