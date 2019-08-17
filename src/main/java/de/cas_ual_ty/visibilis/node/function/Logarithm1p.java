package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

public class Logarithm1p extends FtoF
{
	public Logarithm1p(int posX, int posY)
	{
		super(posX, posY);
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
