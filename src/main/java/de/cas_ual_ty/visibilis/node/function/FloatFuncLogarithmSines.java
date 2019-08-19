package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FloatFunc;

public class FloatFuncLogarithmSines extends FloatFunc
{
	public FloatFuncLogarithmSines()
	{
		super();
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.sin(this.in1.getValue());
	}
	
	@Override
	public String getID()
	{
		return "func_sin";
	}
}
