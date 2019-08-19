package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class VUtility
{
	//read: Instance reading itself from NBT
	//write: Instance writing itself from NBT
	//save: Creating an NBT from the data of the instance
	//load: Creating a basic instance from NBT data
	
	public static Print loadPrintFromNBT(NBTTagCompound nbt)
	{
		Print p = new Print();
		readPrintFromNBT(p, nbt);
		readPrintConnectionsFromNBT(p, nbt);
		
		return p;
	}
	
	public static NBTTagCompound savePrintToNBT(Print p)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writePrintToNBT(p, nbt);
		writePrintConnectionsToNBT(p, nbt);
		
		return nbt;
	}
	
	public static void readPrintFromNBT(Print p, NBTTagCompound nbt)
	{
		p.readFromNBT(nbt);
		readPrintNodesFromNBT(p, nbt);
	}
	
	public static void writePrintToNBT(Print p, NBTTagCompound nbt)
	{
		p.writeToNBT(nbt);
		writePrintNodesToNBT(p, nbt);
	}
	
	public static void readPrintNodesFromNBT(Print p, NBTTagCompound nbt)
	{
		NBTTagList nbtlist = nbt.getTagList("nodes", Constants.NBT.TAG_COMPOUND);
		
		NBTTagCompound nbt1;
		Node n;
		
		for(int i = 0; i < nbtlist.tagCount(); ++i)
		{
			nbt1 = nbtlist.getCompoundTagAt(i);
			
			n = loadNodeFromNBT(nbt1);
			
			if(n != null)
			{
				p.addNode(n);
			}
			else
			{
				Visibilis.error("Could not load node from NBT!");
			}
		}
	}
	
	public static void writePrintNodesToNBT(Print p, NBTTagCompound nbt)
	{
		NBTTagList nbtlist = new NBTTagList();
		
		NBTTagCompound nbt1;
		Node n;
		
		for(int i = 0; i < p.getNodes().size(); ++i)
		{
			n = p.getNodes().get(i);
			
			nbt1 = saveNodeToNBT(n);
			
			if(nbt1 != null)
			{
				nbtlist.appendTag(nbt1);
			}
			else
			{
				Visibilis.error("Could not write node to NBT!");
			}
		}
		
		nbt.setTag("nodes", nbtlist);
	}
	
	public static Node loadNodeFromNBT(NBTTagCompound nbt)
	{
		Node n;
		
		String id = nbt.getString("id");
		
		n = VRegistry.INSTANCE.instantiateNode(id);
		
		if(n == null)
		{
			return null;
		}
		
		readNodeFromNBT(n, nbt);
		
		return n;
	}
	
	public static NBTTagCompound saveNodeToNBT(Node n)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		String name = VRegistry.INSTANCE.getNameForNode(n);
		
		if(name == null)
		{
			return null;
		}
		
		nbt.setString("id", name);
		
		writeNodeToNBT(n, nbt);
		
		return nbt;
	}
	
	public static void readNodeFromNBT(Node n, NBTTagCompound nbt)
	{
		n.readNodeFromNBT(nbt);
	}
	
	public static void writeNodeToNBT(Node n, NBTTagCompound nbt)
	{
		n.writeNodeToNBT(nbt);
	}
	
	public static void readPrintConnectionsFromNBT(Print p, NBTTagCompound nbt)
	{
		int[] array = nbt.getIntArray("connections");
		
		Node n;
		NodeField f;
		Node n1;
		NodeField f1;
		
		for(int i = 0; i + 3 < array.length; i += 4)
		{
			n = getNodeForIdx(p, 	array[i + 0]);
			f = n.getOutput(		array[i + 1]);
			n1 = getNodeForIdx(p, 	array[i + 2]);
			f1 = n1.getInput(		array[i + 3]);
			
			if(!f.tryConnectTo(f1))
			{
				Visibilis.error("Could not load connection!");
			}
		}
	}
	
	public static void writePrintConnectionsToNBT(Print p, NBTTagCompound nbt)
	{
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		int i;
		int j;
		int k;
		
		Node n;
		NodeField f;
		ArrayList<NodeField> connections;
		Node n1;
		NodeField f1;
		
		for(i = 0; i < p.getNodes().size(); ++i)
		{
			n = p.getNodes().get(i);
			
			for(j = 0; j < n.getOutputAmt(); ++j)
			{
				f = n.getOutput(j);
				connections = f.getConnectionsList();
				
				for(k = 0; k < connections.size(); ++k)
				{
					f1 = connections.get(k);
					n1 = f1.node;
					
					array.add(i);
					array.add(f.id);
					array.add(getIdxForNode(p, n));
					array.add(f1.id);
				}
			}
		}
		
		nbt.setTag("connections", new NBTTagIntArray(array));
	}
	
	public static int getIdxForNode(Print p, Node n)
	{
		Node n1;
		
		for(int i = 0; i < p.getNodes().size(); ++i)
		{
			n1 = p.getNodes().get(i);
			
			if(n1 == n)
			{
				return i;
			}
		}
		
		Visibilis.error("Could not find index for node!");
		
		return -1;
	}
	
	public static Node getNodeForIdx(Print p, int idx)
	{
		for(int i = 0; i < p.getNodes().size(); ++i)
		{
			if(i == idx)
			{
				return p.getNodes().get(i);
			}
		}
		
		Visibilis.error("Could not find node for index!");
		
		return null;
	}
}
