package de.cas_ual_ty.visibilis.node;

import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class NodeType<T extends Node> extends ForgeRegistryEntry<NodeType<?>> implements IForgeRegistryEntry<NodeType<?>>
{
    private IFactory<T> factory;
    private Class<T> nodeClass;
    
    public NodeType(IFactory<T> factory)
    {
        this(factory, (VUtility.convert(factory.create().getClass())));
    }
    
    public NodeType(IFactory<T> factory, Class<T> nodeClass)
    {
        this.factory = factory;
        this.nodeClass = nodeClass;
    }
    
    public T instantiate()
    {
        return this.factory.create();
    }
    
    public IFactory<T> getFactory()
    {
        return this.factory;
    }
    
    public Class<T> getNodeClass()
    {
        return this.nodeClass;
    }
    
    public static interface IFactory<U extends Node>
    {
        U create();
    }
}
