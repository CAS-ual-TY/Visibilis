package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class NtoF extends Node
{
	public final Output<Float> out;
	
	public NtoF()
	{
		super(1, 0);
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
