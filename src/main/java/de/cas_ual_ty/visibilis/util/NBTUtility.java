package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.VRegistry;
import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class NBTUtility
{
    // read: Instance reading itself from NBT
    // write: Instance writing itself to NBT
    // save: Creating an NBT from the instance
    // load: Creating an instance from the NBT
    
    /*
     * A Node's idx (index) is basically the in the list of the print. Using these positions, all connections can be numerically saved and read again. The only condition is that you must load all connections before you modify any node positions. ... -> Modify Print -> Save Nodes -> Save Connections -> Load Nodes -> Load Connections -> Modify Print -> ...
     */
    
    // NBT Keys
    public static final String KEY_PRINT = "print";
    public static final String KEY_NODE = "node";
    public static final String KEY_PRINT_NODES = "nodes";
    public static final String KEY_PRINT_CONNECTIONS = "connections";
    
    /**
     * Creates and loads a new {@link Print} instance from the given NBT tag, including all its nodes and connections
     */
    public static Print loadPrintFromNBT(NBTTagCompound nbt)
    {
        Print p = new Print();
        NBTUtility.readPrintFromNBT(p, nbt);
        
        return p;
    }
    
    /**
     * Creates a new {@link Print} with the given Print instance saved onto it, including all its nodes an connections
     */
    public static NBTTagCompound savePrintToNBT(Print p)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTUtility.writePrintToNBT(p, nbt);
        
        return nbt;
    }
    
    /**
     * Reads a {@link Print} instance's data from the given NBT, excluding nodes and connections
     */
    public static void readPrintFromNBT(Print p, NBTTagCompound nbt)
    {
        NBTTagCompound nbt1 = nbt.getCompoundTag(NBTUtility.KEY_PRINT);
        p.readFromNBT(nbt1);
    }
    
    /**
     * Writes a {@link Print} instance's data to the given NBT, excluding nodes and connections
     */
    public static void writePrintToNBT(Print p, NBTTagCompound nbt)
    {
        NBTTagCompound nbt1 = new NBTTagCompound();
        p.writeToNBT(nbt1);
        nbt.setTag(NBTUtility.KEY_PRINT, nbt1);
    }
    
    /**
     * Reads a {@link Print} instance's node list from the given NBT, excluding connections
     */
    public static void readPrintNodesFromNBT(Print p, NBTTagCompound nbt)
    {
        NBTTagList nbtlist = nbt.getTagList(NBTUtility.KEY_PRINT_NODES, Constants.NBT.TAG_COMPOUND);
        
        NBTTagCompound nbt1;
        Node n;
        
        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            nbt1 = nbtlist.getCompoundTagAt(i);
            
            n = NBTUtility.loadNodeFromNBT(nbt1);
            
            if (n != null)
            {
                p.addNode(n);
            }
            else
            {
                Visibilis.error("Could not load node from NBT!");
            }
        }
    }
    
    /**
     * Writes a {@link Print} instance's node list to the given NBT, excluding connections
     */
    public static void writePrintNodesToNBT(Print p, NBTTagCompound nbt)
    {
        NBTTagList nbtlist = new NBTTagList();
        
        NBTTagCompound nbt1;
        Node n;
        
        for (int i = 0; i < p.getNodes().size(); ++i)
        {
            n = p.getNodes().get(i);
            
            nbt1 = NBTUtility.saveNodeToNBT(n);
            
            if (nbt1 != null)
            {
                nbtlist.appendTag(nbt1);
            }
            else
            {
                Visibilis.error("Could not write node to NBT!");
            }
        }
        
        nbt.setTag(NBTUtility.KEY_PRINT_NODES, nbtlist);
    }
    
    /**
     * Creates and loads a new {@link Node} instance from the given NBT tag, excluding all its connections
     */
    public static Node loadNodeFromNBT(NBTTagCompound nbt)
    {
        Node n;
        
        String id = nbt.getString(NBTUtility.KEY_NODE);
        
        n = VRegistry.INSTANCE.instantiateNode(id);
        
        if (n == null)
        {
            return null;
        }
        
        NBTUtility.readNodeFromNBT(n, nbt);
        
        return n;
    }
    
    /**
     * Creates a new NBT with the given {@link Node} instance saved onto it, excluding all its connections
     */
    public static NBTTagCompound saveNodeToNBT(Node n)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        
        String name = VRegistry.INSTANCE.getNameForNode(n);
        
        if (name == null)
        {
            return null;
        }
        
        nbt.setString(NBTUtility.KEY_NODE, name);
        
        NBTUtility.writeNodeToNBT(n, nbt);
        
        return nbt;
    }
    
    /**
     * Reads a {@link Node} instance's data from the given NBT, excluding connections
     */
    public static void readNodeFromNBT(Node n, NBTTagCompound nbt)
    {
        n.readNodeFromNBT(nbt);
    }
    
    /**
     * Writes a {@link Node} instance's data to the given NBT, excluding connections
     */
    public static void writeNodeToNBT(Node n, NBTTagCompound nbt)
    {
        n.writeNodeToNBT(nbt);
    }
    
    /**
     * Reads all connections of this {@link Print} instance's node list from the NBT
     */
    public static void readPrintConnectionsFromNBT(Print p, NBTTagCompound nbt)
    {
        int[] array = nbt.getIntArray(NBTUtility.KEY_PRINT_CONNECTIONS);
        
        Node n;
        NodeField f;
        Node n1;
        NodeField f1;
        
        for (int i = 0; i + 3 < array.length; i += 4)
        {
            n = Print.getNodeForIdx(p, array[i + 0]);
            f = n.getOutput(array[i + 1]);
            n1 = Print.getNodeForIdx(p, array[i + 2]);
            f1 = n1.getInput(array[i + 3]);
            
            if (!NodeField.tryConnect(f, f1))
            {
                Visibilis.error("Could not load connection! Connecting failed.");
            }
        }
    }
    
    /**
     * Writes all connections of this {@link Print} instance's node list to the NBT
     */
    public static void writePrintConnectionsToNBT(Print p, NBTTagCompound nbt)
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        
        int i;
        int j;
        int k;
        
        Node n;
        NodeField f;
        ArrayList<NodeField> connections;
        NodeField f1;
        
        for (i = 0; i < p.getNodes().size(); ++i)
        {
            n = p.getNodes().get(i);
            
            for (j = 0; j < n.getOutputAmt(); ++j)
            {
                f = n.getOutput(j);
                connections = f.getConnectionsList();
                
                for (k = 0; k < connections.size(); ++k)
                {
                    f1 = connections.get(k);
                    array.add(i);
                    array.add(f.getId());
                    array.add(Print.getIdxForNode(p, f1.node));
                    array.add(f1.getId());
                }
            }
        }
        
        nbt.setTag(NBTUtility.KEY_PRINT_CONNECTIONS, new NBTTagIntArray(array));
    }
}
