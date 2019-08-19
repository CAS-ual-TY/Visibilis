package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

public class LogarithmE extends FtoF
{
	public LogarithmE()
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
