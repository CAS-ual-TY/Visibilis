package de.cas_ual_ty.visibilis.node.event;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeEvent extends Node implements INodeExec
{
    public final Output<Object> out1Exec;
    
    /**
     * The modId:type of this event.
     */
    public String eventType;
    
    public NodeEvent(String modId, String eventType)
    {
        super();
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.eventType = modId + ":" + eventType;
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
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
}
