package de.cas_ual_ty.visibilis.node.event;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.nbt.CompoundNBT;

public class NodeEvent extends Node implements INodeExec
{
    // NBT Keys
    public static final String KEY_EVENT_TYPE = "eventType";
    
    public final Output outExec;
    
    /**
     * The modId:type of this event.
     */
    public String eventType;
    
    public NodeEvent(int outputAmt)
    {
        super(outputAmt, 0);
        this.outExec = new Output(this, DataType.EXEC, "exec");
        this.eventType = null; // Just to make sure it is initialized
    }
    
    public NodeEvent()
    {
        this(1);
    }
    
    public NodeEvent(int outputAmt, String modId, String eventType)
    {
        this(outputAmt);
        this.eventType = modId + ":" + eventType;
    }
    
    public NodeEvent(String modId, String eventType)
    {
        this(1, modId, eventType);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
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
     * 
     * @return modId:type
     */
    public String getEventType()
    {
        return this.eventType;
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.outExec : null;
    }
    
    @Override
    public String getID()
    {
        return "event_" + this.getEventType();
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        super.readNodeFromNBT(nbt);
        
        this.eventType = nbt.getString(NodeEvent.KEY_EVENT_TYPE);
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        
        nbt.putString(NodeEvent.KEY_EVENT_TYPE, this.getEventType());
    }
}
