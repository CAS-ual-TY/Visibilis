package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.node.general.NodeBoolean2to1;

public class NodeOR extends NodeBoolean2to1
{
    public NodeOR()
    {
        super();
    }
    
    @Override
    protected boolean calculate(boolean in1, boolean in2)
    {
        return in1 || in2;
    }
    
    @Override
    public String getID()
    {
        return "or";
    }
}
