package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.IItoI;

public class IItoIMultiply extends IItoI
{
	public IItoIMultiply(int posX, int posY)
	{
		super(posX, posY);
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 * in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_i_multiply";
	}
}
