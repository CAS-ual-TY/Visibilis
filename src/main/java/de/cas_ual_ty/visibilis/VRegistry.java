package de.cas_ual_ty.visibilis;

import java.util.HashMap;

import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class VRegistry
{
	public static final VRegistry INSTANCE = new VRegistry();
	
	private final HashMap<String, Class<? extends Node>> mapNodes;
	
	public VRegistry()
	{
		this.mapNodes = new HashMap<String, Class<? extends Node>>();
	}
	
	// --- NODES START ---
	
	public void registerNode(Class<? extends Node> c, String modId, String name)
	{
		this.registerNode(c, modId + ":" + name);
	}
	
	public void registerNode(Class<? extends Node> c, ResourceLocation rl)
	{
		this.registerNode(c, rl.toString());
	}
	
	public void registerNode(Class<? extends Node> c, String modIdName)
	{
		modIdName = GameData.checkPrefix(modIdName, false).toString();
		
		if(hasEmptyConstructor(c))
		{
			this.mapNodes.put(modIdName, c);
		}
		else
		{
			Visibilis.error("Node \"" + modIdName + "\" could not be registered!");
		}
	}
	
	// --- ---
	
	public Node instantiateNode(String modId, String name)
	{
		return this.instantiateNode(modId + ":" + name);
	}
	
	public Node instantiateNode(ResourceLocation rl)
	{
		return this.instantiateNode(rl.toString());
	}
	
	public Node instantiateNode(String modIdName)
	{
		if(this.mapNodes.containsKey(modIdName))
		{
			try
			{
				return this.mapNodes.get(modIdName).newInstance();
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				Visibilis.error("Node \"" + modIdName + "\" could not be instantiated despite being registered!");
			}
		}
		
		Visibilis.error("Node \"" + modIdName + "\" does not exist!");
		
		return null;
	}
	
	// --- NODES END ---
	
	private static boolean hasEmptyConstructor(Class c)
	{
		try
		{
			c.newInstance();
			return true;
		}
		catch (IllegalAccessException e)
		{
			Visibilis.error("Class \"" + c.getPackage().toString() + "." + c.getName() + "\" is missing an empty constructor!");
		}
		catch (InstantiationException e)
		{
			Visibilis.error("Class \"" + c.getPackage().toString() + "." + c.getName() + "\" is abstract!");
		}
		
		return false;
	}
}
