package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FloatFunc;

public class FloatFuncLogarithmE extends FloatFunc
{
    public FloatFuncLogarithmE()
    {
        super();
    }
    
    @Override
    protected float calculate(float in1)
    {
        return (float) Math.log(in1);
    }
    
    @Override
    public String getID()
    {
        return "func_log_e";
    }
}
