package de.cas_ual_ty.visibilis.function;

import de.cas_ual_ty.visibilis.general.FtoF;

public class Cosines extends FtoF
{
	public Cosines(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.cos(this.in1.getValue());
	}
}
