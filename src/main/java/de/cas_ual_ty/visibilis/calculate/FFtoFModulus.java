package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.FFtoF;

public class FFtoFModulus extends FFtoF
{
	public FFtoFModulus(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected boolean canCalculate(float in1, float in2)
	{
		return in2 != 0F;
	}
	
	@Override
	protected float calculate(float in1, float in2)
	{
		return in1 % in2;
	}
}
