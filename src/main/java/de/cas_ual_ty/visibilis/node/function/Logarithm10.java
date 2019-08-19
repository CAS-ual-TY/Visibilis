package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

public class Logarithm10 extends FtoF
{
	public Logarithm10()
	{
		super();
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.log10(in1);
	}
	
	@Override
	public String getID()
	{
		return "func_log_10";
	}
}
