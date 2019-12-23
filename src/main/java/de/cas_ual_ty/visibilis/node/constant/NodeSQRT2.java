package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatC;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeFloatC
{
    public NodeSQRT2()
    {
        super();
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
