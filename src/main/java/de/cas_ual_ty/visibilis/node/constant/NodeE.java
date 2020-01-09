package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatC;

public class NodeE extends NodeFloatC
{
    public NodeE()
    {
        super();
    }
    
    @Override
    protected Float getConstant()
    {
        return (float)Math.E;
    }
    
    @Override
    public String getID()
    {
        return "e";
    }
}
