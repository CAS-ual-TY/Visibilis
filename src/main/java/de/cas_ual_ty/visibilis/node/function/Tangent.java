package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.node.general.FtoF;

public class Tangent extends FtoF
{
	public Tangent(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float calculate(float in1)
	{
		return (float) Math.tan(this.in1.getValue());
	}
}
