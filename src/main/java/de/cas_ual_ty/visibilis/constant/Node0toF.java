package de.cas_ual_ty.visibilis.constant;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.Node;
import de.cas_ual_ty.visibilis.Output;

public abstract class Node0toF extends Node
{
	public final Output<Float> out;
	
	public Node0toF(int posX, int posY)
	{
		super(posX, posY, 1, 2);
		this.out = new Output<Float>(0, this, EnumVDataType.FLOAT.dataTypeString, "float");
	}
	
	@Override
	public boolean doCalculate()
	{
		return true;
	}
	
	/**
	 * @return The static value.
	 */
	protected abstract float getValue();
	
	@Override
	public <B> B getOutputValue(int index)
	{
		switch(index)
		{
			case 0: return (B) (Float) this.getValue();
		}
		
		return null;
	}
}
