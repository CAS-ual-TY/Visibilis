package de.cas_ual_ty.visibilis.node.event;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

public class Event extends NodeExec
{
	public final Output outExec;
	
	/**
	 * The mod id of the mod that owns this event.
	 */
	public final String modId;
	
	/**
	 * An unique identification for the event.
	 */
	public final String eventType;
	
	public Event(int assignedID, int outputAmt, int inputAmt, String modId, String eventType)
	{
		super(assignedID, outputAmt, inputAmt);
		this.outExec = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.modId = modId;
		this.eventType = eventType;
	}
	
	@Override
	public boolean doCalculate()
	{
		return true;
	}
	
	@Override
	public <B> B getOutputValue(int index)
	{
		return null;
	}
	
	/**
	 * Get the unique event identifier of this event.
	 * @return {@link #modId} + ":" + {@link #eventType}
	 */
	public String getEventType()
	{
		return this.modId + ":" + this.eventType;
	}

	@Override
	public Output getOutExec(int index)
	{
		return null;
	}

	@Override
	public String getID()
	{
		return "event_" + this.eventType;
	}
}
