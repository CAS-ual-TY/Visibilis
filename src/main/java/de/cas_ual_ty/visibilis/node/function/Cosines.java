package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

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
