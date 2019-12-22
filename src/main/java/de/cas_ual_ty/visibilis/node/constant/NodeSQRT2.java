package de.cas_ual_ty.visibilis.node.constant;

import de.cas_ual_ty.visibilis.node.base.number.NodeNumberC;
import net.minecraft.util.math.MathHelper;

public class NodeSQRT2 extends NodeNumberC
{
    public NodeSQRT2()
    {
        super();
    }
    
    @Override
    protected Number getConstant()
    {
        return MathHelper.SQRT_2;
    }
    
    @Override
    public String getID()
    {
        return "sqrt_2";
    }
}
