package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FloatFunc;

public class FloatFuncCosines extends FloatFunc
{
	public FloatFuncCosines()
	{
		super();
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.cos(this.in1.getValue());
	}

	@Override
	public String getID()
	{
		return "func_cos";
	}
}
