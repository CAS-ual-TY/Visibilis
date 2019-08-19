package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.Float2to1;

public class FloatPower extends Float2to1
{
    public FloatPower()
    {
        super();
    }

    @Override
    protected boolean canCalculate(float in1, float in2)
    {
        // If in1 < 0 and in2 is not rounded, it would mean that you are trying to root a negative value.
        return in1 >= 0 || in2 != (int) in2;
    }

    @Override
    protected float calculate(float in1, float in2)
    {
        return (float) Math.pow(in1, in2);
    }

    @Override
    public String getID()
    {
        return "calc_f_pow";
    }
}
