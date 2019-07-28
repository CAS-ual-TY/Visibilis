package de.cas_ual_ty.visibilis.function;

import de.cas_ual_ty.visibilis.general.FtoF;

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
}
