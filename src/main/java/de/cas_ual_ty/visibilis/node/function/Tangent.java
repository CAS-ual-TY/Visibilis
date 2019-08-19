package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

public class Tangent extends FtoF
{
	public Tangent()
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
