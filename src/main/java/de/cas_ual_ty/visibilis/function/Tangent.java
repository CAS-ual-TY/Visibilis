package de.cas_ual_ty.visibilis.function;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.Input;
import de.cas_ual_ty.visibilis.Node;
import de.cas_ual_ty.visibilis.Output;
import de.cas_ual_ty.visibilis.general.FtoF;

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
