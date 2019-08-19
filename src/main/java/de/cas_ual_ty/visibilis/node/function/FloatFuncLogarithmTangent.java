package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.Float1to1;

public class FloatFuncLogarithmTangent extends Float1to1
{
	public FloatFuncLogarithmTangent()
	{
		super();
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.tan(this.in1.getValue());
	}
	
	@Override
	public String getID()
	{
		return "func_tan";
	}
}
