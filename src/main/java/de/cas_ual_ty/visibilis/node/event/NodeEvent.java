package de.cas_ual_ty.visibilis.node.event;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.nbt.CompoundNBT;

public class NodeEvent extends Node implements INodeExec
{
    // NBT Keys
    public static final String KEY_EVENT_TYPE = "eventType";
    
    public final Output<Object> out1Exec;
    
    /**
     * The modId:type of this event.
     */
    public String eventType;
    
    public NodeEvent()
    {
        super();
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.eventType = null; // Just to make sure it is initialized
    }
    
    public NodeEvent(String modId, String eventType)
    {
        this();
        this.eventType = modId + ":" + eventType;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
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
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
    }
    
    @Override
    public String getID()
    {
        return "event." + this.getEventType();
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
