package de.cas_ual_ty.visibilis.node;

import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class NodeType<T extends Node> extends ForgeRegistryEntry<NodeType<?>> implements IForgeRegistryEntry<NodeType<?>>
{
    private IFactory<T> factory;
    
    public NodeType(IFactory<T> factory)
    {
        this.factory = factory;
    }
    
    public T instantiate()
    {
        return this.factory.create(this);
    }
    
    public IFactory<T> getFactory()
    {
        return this.factory;
    }
    
    public static interface IFactory<U extends Node>
    {
        U create(NodeType<U> type);
    }
}
