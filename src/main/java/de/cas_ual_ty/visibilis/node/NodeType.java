package de.cas_ual_ty.visibilis.node;

import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class NodeType<T extends Node> extends ForgeRegistryEntry<NodeType<?>> implements IForgeRegistryEntry<NodeType<?>>
{
    private IFactory<T> factory;
    protected String eventType;
    protected boolean invisible;
    
    public NodeType(IFactory<T> factory)
    {
        this.factory = factory;
        this.eventType = null;
        this.invisible = false;
    }
    
    public T instantiate()
    {
        return this.factory.create(this);
    }
    
    public IFactory<T> getFactory()
    {
        return this.factory;
    }
    
    public NodeType<T> setIsEvent(String modId, String eventType)
    {
        return this.setIsEvent(modId + ":" + eventType);
    }
    
    protected NodeType<T> setIsEvent(String eventType)
    {
        this.eventType = eventType;
        return this;
    }
    
    public boolean isEvent()
    {
        return this.eventType != null;
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
    
    public NodeType<T> setInvisible()
    {
        this.invisible = true;
        return this;
    }
    
    /**
     * @return <b>true</b> to hide these kinds of node in the Node List component of the GUI
     */
    public boolean isInvisible()
    {
        return this.invisible;
    }
    
    public static interface IFactory<U extends Node>
    {
        U create(NodeType<U> type);
    }
}
