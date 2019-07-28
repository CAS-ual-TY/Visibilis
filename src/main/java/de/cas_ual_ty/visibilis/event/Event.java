package de.cas_ual_ty.visibilis.event;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.Node;
import de.cas_ual_ty.visibilis.Output;

public class Event extends Node
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
	
	public Event(int posX, int posY, int outputAmt, int inputAmt, String modId, String eventType)
	{
		super(posX, posY, outputAmt, inputAmt);
		this.outExec = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.modId = modId;
		this.eventType = eventType;
	}
	
	public Event(int posX, int posY, int inputAmt, String modId, String eventType)
	{
		this(posX, posY, 1, inputAmt, modId, eventType);
	}
	
	public Event(int posX, int posY, String modId, String eventType)
	{
		this(posX, posY, 0, modId, eventType);
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
}
