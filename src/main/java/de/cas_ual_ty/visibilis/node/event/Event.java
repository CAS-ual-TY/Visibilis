package de.cas_ual_ty.visibilis.node.event;

import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;
import net.minecraft.nbt.NBTTagCompound;

public class Event extends NodeExec
{
	//NBT Keys
	public static final String KEY_EVENT_TYPE = "eventType";
	
	public final Output outExec;
	
	/**
	 * The modId:type of this event.
	 */
	public String eventType;
	
	public Event(int outputAmt)
	{
		super(outputAmt, 0);
		this.outExec = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
		this.eventType = null; //Just to make sure it is initialized
	}
	
	public Event()
	{
		this(0);
	}
	
	public Event(int outputAmt, String modId, String eventType)
	{
		this(outputAmt);
		this.eventType = modId + ":" + eventType;
	}
	
	public Event(String modId, String eventType)
	{
		this(1, modId, eventType);
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
	 * @return modId:type
	 */
	public String getEventType()
	{
		return this.eventType;
	}

	@Override
	public Output getOutExec(int index)
	{
		return null;
	}

	@Override
	public String getID()
	{
		return "event_" + this.getEventType();
	}
	
	@Override
	public void readNodeFromNBT(NBTTagCompound nbt)
	{
		super.readNodeFromNBT(nbt);
		
		this.eventType = nbt.getString(KEY_EVENT_TYPE);
	}
	
	@Override
	public void writeNodeToNBT(NBTTagCompound nbt)
	{
		super.writeNodeToNBT(nbt);
		
		nbt.setString(KEY_EVENT_TYPE, this.getEventType());
	}
}
