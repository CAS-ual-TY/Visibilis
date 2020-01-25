package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatConstant;

public class NodeE extends NodeFloatConstant
{
    public NodeE(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float getConstant()
    {
        return (float)Math.E;
    }
}
