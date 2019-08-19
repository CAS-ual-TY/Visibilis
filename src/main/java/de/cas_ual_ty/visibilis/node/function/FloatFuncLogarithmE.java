package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.Float1to1;

public class FloatFuncLogarithmE extends Float1to1
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
