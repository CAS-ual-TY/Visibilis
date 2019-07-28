package de.cas_ual_ty.visibilis.function;

import de.cas_ual_ty.visibilis.general.FtoF;

public class Logarithm10 extends FtoF
{
	public Logarithm10(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.log10(in1);
	}
}
