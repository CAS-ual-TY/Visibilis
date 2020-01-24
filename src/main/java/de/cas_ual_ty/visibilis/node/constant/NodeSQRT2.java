package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatConstant;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeFloatConstant
{
    public NodeSQRT2(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    protected Float getConstant()
    {
        return MathHelper.SQRT_2;
    }
    
    @Override
    public String getID()
    {
        return "sqrt_2";
    }
}
