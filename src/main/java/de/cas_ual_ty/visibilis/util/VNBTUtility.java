package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.NodesRegistry;
import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Constants.NBT;

public class VNBTUtility
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
    public static Print loadPrintFromNBT(CompoundNBT nbt)
    {
        Print p = new Print();
        VNBTUtility.readPrintFromNBT(p, nbt);
        
        return p;
    }
    
    /**
     * Creates a new {@link Print} with the given Print instance saved onto it, including all its nodes an connections
     */
    public static CompoundNBT savePrintToNBT(Print p)
    {
        CompoundNBT nbt = new CompoundNBT();
        VNBTUtility.writePrintToNBT(p, nbt);
        
        return nbt;
    }
    
    /**
     * Reads a {@link Print} instance's data from the given NBT, excluding nodes and connections
     */
    public static void readPrintFromNBT(Print p, CompoundNBT nbt)
    {
        CompoundNBT nbt1 = nbt.getCompound(VNBTUtility.KEY_PRINT);
        p.readFromNBT(nbt1);
    }
    
    /**
     * Writes a {@link Print} instance's data to the given NBT, excluding nodes and connections
     */
    public static void writePrintToNBT(Print p, CompoundNBT nbt)
    {
        CompoundNBT nbt1 = new CompoundNBT();
        p.writeToNBT(nbt1);
        nbt.put(VNBTUtility.KEY_PRINT, nbt1);
    }
    
    /**
     * Reads a {@link Print} instance's node list from the given NBT, excluding connections
     */
    public static void readPrintNodesFromNBT(Print p, CompoundNBT nbt)
    {
        ListNBT nbtlist = nbt.getList(VNBTUtility.KEY_PRINT_NODES, Constants.NBT.TAG_COMPOUND);
        
        CompoundNBT nbt1;
        Node n;
        
        for(int i = 0; i < nbtlist.size(); ++i)
        {
            nbt1 = nbtlist.getCompound(i);
            
            n = VNBTUtility.loadNodeFromNBT(nbt1);
            
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
    
    /**
     * Writes a {@link Print} instance's node list to the given NBT, excluding connections
     */
    public static void writePrintNodesToNBT(Print p, CompoundNBT nbt)
    {
        ListNBT nbtlist = new ListNBT();
        
        CompoundNBT nbt1;
        Node n;
        
        for(int i = 0; i < p.getNodes().size(); ++i)
        {
            n = p.getNodes().get(i);
            
            nbt1 = VNBTUtility.saveNodeToNBT(n);
            
            if(nbt1 != null)
            {
                nbtlist.add(nbt1);
            }
            else
            {
                Visibilis.error("Could not write node to NBT!");
            }
        }
        
        nbt.put(VNBTUtility.KEY_PRINT_NODES, nbtlist);
    }
    
    /**
     * Creates and loads a new {@link Node} instance from the given NBT tag, excluding all its connections
     */
    public static Node loadNodeFromNBT(CompoundNBT nbt)
    {
        Node n;
        
        String id = nbt.getString(VNBTUtility.KEY_NODE);
        
        n = NodesRegistry.instantiateNode(id);
        
        if(n == null)
        {
            return null;
        }
        
        VNBTUtility.readNodeFromNBT(n, nbt);
        
        return n;
    }
    
    /**
     * Creates a new NBT with the given {@link Node} instance saved onto it, excluding all its connections
     */
    public static CompoundNBT saveNodeToNBT(Node n)
    {
        CompoundNBT nbt = new CompoundNBT();
        
        String name = NodesRegistry.getNameForNode(n);
        
        if(name == null)
        {
            return null;
        }
        
        nbt.putString(VNBTUtility.KEY_NODE, name);
        
        VNBTUtility.writeNodeToNBT(n, nbt);
        
        return nbt;
    }
    
    /**
     * Reads a {@link Node} instance's data from the given NBT, excluding connections
     */
    public static void readNodeFromNBT(Node n, CompoundNBT nbt)
    {
        n.readNodeFromNBT(nbt);
    }
    
    /**
     * Writes a {@link Node} instance's data to the given NBT, excluding connections
     */
    public static void writeNodeToNBT(Node n, CompoundNBT nbt)
    {
        n.writeNodeToNBT(nbt);
    }
    
    /**
     * Reads all connections of this {@link Print} instance's node list from the NBT
     */
    public static void readPrintConnectionsFromNBT(Print p, CompoundNBT nbt)
    {
        int[] array = nbt.getIntArray(VNBTUtility.KEY_PRINT_CONNECTIONS);
        
        Node n;
        NodeField<?> f;
        Node n1;
        NodeField<?> f1;
        
        for(int i = 0; i + 3 < array.length; i += 4)
        {
            n = Print.getNodeForIdx(p, array[i + 0]);
            n1 = Print.getNodeForIdx(p, array[i + 2]);
            
            if(n == null || n1 == null)
            {
                continue;
            }
            
            f = n.getOutput(array[i + 1]);
            f1 = n1.getInput(array[i + 3]);
            
            if(!NodeField.tryConnect(f, f1))
            {
                Visibilis.error("Could not load connection! Connecting failed.");
            }
        }
    }
    
    /**
     * Writes all connections of this {@link Print} instance's node list to the NBT
     */
    public static void writePrintConnectionsToNBT(Print p, CompoundNBT nbt)
    {
        ArrayList<Integer> array = new ArrayList<>();
        
        int i;
        int j;
        int k;
        
        Node n;
        NodeField<?> f;
        ArrayList<NodeField<?>> connections;
        NodeField<?> f1;
        
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
                    array.add(i);
                    array.add(f.getId());
                    array.add(Print.getIdxForNode(p, f1.getNode()));
                    array.add(f1.getId());
                }
            }
        }
        nbt.put(VNBTUtility.KEY_PRINT_CONNECTIONS, new IntArrayNBT(array));
    }
    
    public static void printTree(CompoundNBT nbt)
    {
        VNBTUtility.recPrintTree(0, nbt);
    }
    
    private static void recPrintTree(int indent, CompoundNBT nbt)
    {
        for(String key : nbt.keySet())
        {
            Visibilis.debug(VNBTUtility.indent(indent) + key);
            
            if(nbt.get(key) instanceof ListNBT)
            {
                VNBTUtility.recPrintList(indent + 1, nbt.getList(key, NBT.TAG_COMPOUND));
            }
            else if(nbt.get(key) instanceof CompoundNBT)
            {
                VNBTUtility.recPrintTree(indent + 1, nbt.getCompound(key));
            }
        }
    }
    
    private static void recPrintList(int indent, ListNBT nbt0)
    {
        for(int i = 0; i < nbt0.size(); ++i)
        {
            VNBTUtility.recPrintTree(indent + 1, nbt0.getCompound(i));
        }
    }
    
    private static String indent(int amt)
    {
        String s = "";
        
        for(; amt > 0; --amt)
        {
            s += "  ";
        }
        
        return s;
    }
}
