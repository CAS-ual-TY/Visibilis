package de.cas_ual_ty.visibilis.node.calculate;

import de.cas_ual_ty.visibilis.node.general.IItoI;

public class IItoIDivide extends IItoI
{
	public IItoIDivide()
	{
		super();
	}
	
	@Override
	protected int calculate(int in1, int in2)
	{
		return in1 / in2;
	}
	
	@Override
	public String getID()
	{
		return "calc_i_divide";
	}
}
