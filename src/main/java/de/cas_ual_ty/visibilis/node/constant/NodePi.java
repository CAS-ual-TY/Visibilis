package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatConstant;

public class NodePi extends NodeFloatConstant
{
    public NodePi(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float getConstant()
    {
        return (float)Math.PI;
    }
}
