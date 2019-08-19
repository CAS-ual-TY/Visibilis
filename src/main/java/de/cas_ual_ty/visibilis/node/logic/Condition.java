package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

public class Condition extends NodeExec
{
	public final Output outExec1;
	public final Output outExec2;
	public final Input inExec;
	public final Input<Boolean> inBoolean;
	
	public boolean value;
	
	public Condition(int outputAmt, int inputAmt)
	{
		super(outputAmt, inputAmt);
		this.outExec1 = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.outExec2 = new Output(1, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.inExec = new Input(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.inBoolean = new Input<Boolean>(1, this, EnumVDataType.BOOLEAN.dataTypeString, "boolean");
	}
	
	@Override
	public Output getOutExec(int index)
	{
		return index == 0 ? (this.value ? this.outExec1 : this.outExec2) : null;
	}
	
	@Override
	public boolean doCalculate()
	{
		this.value = this.inBoolean.getValue();
		return true;
	}
	
	@Override
	public <B> B getOutputValue(int index)
	{
		return null;
	}

	@Override
	public String getID()
	{
		return "condition";
	}
}
