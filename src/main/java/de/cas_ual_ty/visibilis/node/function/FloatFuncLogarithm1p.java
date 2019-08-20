package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FloatFunc;

public class FloatFuncLogarithm1p extends FloatFunc
{
    public FloatFuncLogarithm1p()
    {
        super();
    }
    
    @Override
    protected float calculate(float in1)
    {
        return (float) Math.log1p(in1);
    }
    
    @Override
    public String getID()
    {
        return "func_log_1p";
    }
}
