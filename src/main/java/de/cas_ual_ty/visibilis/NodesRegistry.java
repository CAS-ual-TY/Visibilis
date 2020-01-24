package de.cas_ual_ty.visibilis;

import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("rawtypes")
public class NodesRegistry
{
    public static Node instantiateNode(String modId, String name)
    {
        return NodesRegistry.instantiateNode(modId + ":" + name);
    }
    
    public static Node instantiateNode(String modIdName)
    {
        return NodesRegistry.instantiateNode(new ResourceLocation(modIdName));
    }
    
    public static Node instantiateNode(ResourceLocation rl)
    {
        if(Visibilis.nodeTypesRegistry.containsKey(rl))
        {
            return Visibilis.nodeTypesRegistry.getValue(rl).instantiate();
        }
        
        Visibilis.error("Node \"" + rl.toString() + "\" does not exist!");
        
        return null;
    }
    
    // --- ---
    
    public static String getNameForNode(Node n)
    {
        ResourceLocation rl = NodesRegistry.getRLForNode(n);
        
        if(rl != null)
        {
            return rl.toString();
        }
        
        return null;
    }
    
    public static ResourceLocation getRLForNode(Node n)
    {
        NodeType type = n.type;
        
        if(type != null)
        {
            return type.getRegistryName();
        }
        
        return null;
    }
    
    public static void forEachRL(Consumer<ResourceLocation> consumer)
    {
        for(ResourceLocation rl : Visibilis.nodeTypesRegistry.getKeys())
        {
            consumer.accept(rl);
        }
    }
    
    public static void forEachNodeType(Consumer<NodeType<?>> consumer)
    {
        NodesRegistry.forEachRL((rl) ->
        {
            consumer.accept(Visibilis.nodeTypesRegistry.getValue(rl));
        });
    }
}
