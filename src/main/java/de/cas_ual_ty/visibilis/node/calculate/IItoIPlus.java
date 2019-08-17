package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.IItoI;

public class IItoIPlus extends IItoI
{
	public IItoIPlus(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 + in2;
	}
}
