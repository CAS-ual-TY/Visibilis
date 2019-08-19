package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.Float1to1;

public class FloatFuncLogarithm1p extends Float1to1
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
