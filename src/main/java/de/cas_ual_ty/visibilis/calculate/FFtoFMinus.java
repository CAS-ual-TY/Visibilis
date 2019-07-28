package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.FFtoF;

public class FFtoFMinus extends FFtoF
{
	public FFtoFMinus(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return in1 - in2;
	}
}
