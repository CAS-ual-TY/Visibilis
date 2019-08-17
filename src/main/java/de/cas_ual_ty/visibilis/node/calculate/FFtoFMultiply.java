package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class FFtoFMultiply extends FFtoF
{
	public FFtoFMultiply(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return in1 * in2;
	}
}
