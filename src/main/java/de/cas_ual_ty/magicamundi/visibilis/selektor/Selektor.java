package de.cas_ual_ty.magicamundi.visibilis.selektor;

import de.cas_ual_ty.magicamundi.visibilis.EnumMMDataType;
import de.cas_ual_ty.magicamundi.visibilis.target.TargetsList;
import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.Node;
import de.cas_ual_ty.visibilis.Output;

public abstract class Selektor extends Node
{
	public final Output outExec;
	public final Output<TargetsList> outTargetsList;
	
	public final TargetsList targetsList;
	
	public Selektor(int posX, int posY, int outputAmt, int inputAmt)
	{
		super(posX, posY, outputAmt, inputAmt);
		this.outExec = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.outTargetsList = new Output<TargetsList>(1, this, EnumMMDataType.TARGETS_LIST.dataTypeString, "targets_list");
		this.targetsList = new TargetsList();
	}
	
	public Selektor(int posX, int posY, int outputAmt)
	{
		this(posX, posY, outputAmt, 0);
	}
	
	public Selektor(int posX, int posY)
	{
		this(posX, posY, 1);
	}
	
	@Override
	public boolean doCalculate()
	{
		this.targetsList.clear();
		return this.findTargets(this.targetsList);
	}
	
	/**
	 * Find/select all targets and add them to the given TargetsList.
	 * @param list The list to add all found/selected targets to.
	 * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
	 */
	public abstract boolean findTargets(TargetsList list);
	
	@Override
	public <B> B getOutputValue(int index)
	{
		switch(index)
		{
			case 1: return (B) this.targetsList;
		}
		
		return null;
	}
}
