package de.cas_ual_ty.visibilis;

import javax.annotation.Nullable;

public abstract class NodeExec extends Node
{
	public NodeExec(int posX, int posY, int outputAmt, int inputAmt)
	{
		super(posX, posY, outputAmt, inputAmt);
	}
	
	/**
	 * After calculation, get the next output of the execution data type which basically just decides the next node to calculate. The next node's non calculated parents are calculated before.
	 * @return The next node in execution order.
	 */
	@Nullable
	public abstract Output getOutExec(int index);
}
