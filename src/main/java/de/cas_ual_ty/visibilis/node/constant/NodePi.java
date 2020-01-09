package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatC;

public class NodePi extends NodeFloatC
{
    public NodePi()
    {
        super();
    }
    
    @Override
    protected Float getConstant()
    {
        return (float)Math.PI;
    }
    
    @Override
    public String getID()
    {
        return "pi";
    }
}
