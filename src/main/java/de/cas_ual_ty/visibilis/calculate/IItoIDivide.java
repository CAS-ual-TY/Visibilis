package de.cas_ual_ty.visibilis.calculate;

import de.cas_ual_ty.visibilis.general.IItoI;

public class IItoIDivide extends IItoI
{
	public IItoIDivide(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 / in2;
	}
}
