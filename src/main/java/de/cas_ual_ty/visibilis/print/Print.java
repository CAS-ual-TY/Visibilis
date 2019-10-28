package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;

public class Print
{
    /*
     * Explanation: The Print type is just my own take and implementation of all the Nodes. Technically, you can make your own implementation. But this one comes in with a Gui, full NBT saving/loading, Execution, Synching, and an Item. But, your choice :P
     */
    
    // NBT Keys
    public static final String KEY_POS_X = "posX";
    public static final String KEY_POS_Y = "posY";
    public static final String KEY_ZOOM = "zoom";
    
    /**
     * Where the user currently shifted the print in the GUI. Saved so that they start off again where they last left
     */
    public int posX, posY;
    
    /**
     * How much the user currently zoomed in the GUI. Saved so that they start off again where they last left
     */
    public float zoom = .5F;
    
    /**
     * All nodes in this print (including events).
     */
    protected final ArrayList<Node> nodes = new ArrayList<Node>();
    
    /**
     * All event nodes in this print
     */
    protected final ArrayList<NodeEvent> events = new ArrayList<NodeEvent>();
    
    public Print()
    {
        this.posX = 0;
        this.posY = 0;
    }
    
    /**
     * Add a node to this print. It will be added on top. If the node is already present it will be moved to the top.
     * 
     * @param node
     *            The node to be added.
     * @return The print instance itself.
     */
    public Print addNode(Node node)
    {
        if (this.containsNode(node))
        {
            // Remove node from the list to add it on top again
            this.removeNodeKeepConnections(node);
        }
        
        this.nodes.add(node);
        
        if (node instanceof NodeEvent)
        {
            this.events.add((NodeEvent) node);
        }
        
        return this;
    }
    
    /**
     * Remove a node from the print and disconnect it from all nodes. Used to delete a node safely.
     * 
     * @param node
     *            The node to be removed and disconnected.
     * @return <b>true</b> if this print contained the given node.
     */
    public boolean removeNode(Node node)
    {
        node.disconnect();
        return this.removeNodeKeepConnections(node);
    }
    
    /**
     * Remove a node from the print but keep the connections. Used to reposition the node in the node list.
     * 
     * @param node
     *            The node to be removed.
     * @return <b>true</b> if this print contained the given node.
     */
    public boolean removeNodeKeepConnections(Node node)
    {
        if (node instanceof NodeEvent)
        {
            this.events.remove(node);
        }
        
        return this.nodes.remove(node);
    }
    
    /**
     * @param node
     *            The node to check for.
     * @return <b>true</b> if the given node is part of this print.
     */
    public boolean containsNode(Node node)
    {
        return this.nodes.contains(node);
    }
    
    /**
     * @return The list of nodes (no clone or copy).
     */
    public ArrayList<Node> getNodes()
    {
        return this.nodes;
    }
    
    public boolean executeEvent(String modId, String eventType, ICommandSender sender)
    {
        return this.executeEvent(modId + ":" + eventType, sender);
    }
    
    /**
     * @param eventType
     * @return
     */
    public boolean executeEvent(String eventType, ICommandSender sender)
    {
        NodeEvent event;
        
        // Start from back of list (= on top of gui) to front of list (= on bottom of gui)
        for (int i = this.events.size() - 1; i >= 0; --i)
        {
            event = this.events.get(i);
            
            if (event.eventType.equals(eventType))
            {
                return this.execute(event, new ExecProvider(sender));
            }
        }
        
        return false;
    }
    
    /**
     * Executes all connected exec nodes in succession to the given parameter ({@link #exec(NodeExec)}). Then resets all nodes.
     * 
     * @param node
     *            The node to start the exec chain from.
     * @return <b>true</b> if the given parameter exec node and all child exec nodes could be calculated successfully.
     */
    public boolean execute(NodeExec node, ExecProvider provider)
    {
        boolean ret = this.exec(node, provider);
        
        for (Node n : this.nodes)
        {
            n.resetValues();
        }
        
        return ret;
    }
    
    /**
     * Recursive method. Executes all connected exec nodes in succession to the given parameter.
     * 
     * @param node
     *            The node to start the exec chain from.
     * @return <b>true</b> if the given parameter exec node and all child exec nodes could be calculated successfully.
     */
    protected boolean exec(NodeExec node, ExecProvider provider)
    {
        // If the node can not be calculated, abort
        if (!node.calculate(provider))
        {
            return false;
        }
        
        Output out;
        Input in;
        NodeExec next;
        int i = 0;
        
        /*
         * Now to explain the following segment and loop... You might be asking: "Why a loop? Why not just a single connection?" And the answer is in the question: Loops! This way, you can make nodes which can actually loop. Because for every exec node it will keep looping through, until no further sub node can be found.
         */
        
        // Loop through all outputs of the exec type
        while ((out = node.getOutExec(i)) != null)
        {
            // Get the connected input of the next node
            in = (Input) out.getConnections().get(0);
            
            if (in != null)
            {
                // Get the node of the input
                next = (NodeExec) in.node;
                
                if (next != null)
                {
                    // Check the the sub node has been successfully calculated
                    if (!this.exec(next, provider))
                    {
                        // Abort if false
                        return false;
                    }
                }
                
                // Increment to get the next sub node once the current one is finished calculating
                ++i;
            }
        }
        
        return true;
    }
    
    /**
     * Read from NBT.
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.posX = nbt.getInteger(Print.KEY_POS_X);
        this.posY = nbt.getInteger(Print.KEY_POS_Y);
        this.zoom = nbt.getFloat(Print.KEY_ZOOM);
        
        NBTUtility.readPrintNodesFromNBT(this, nbt);
        NBTUtility.readPrintConnectionsFromNBT(this, nbt);
    }
    
    /**
     * Write to NBT.
     */
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger(Print.KEY_POS_X, this.posX);
        nbt.setInteger(Print.KEY_POS_Y, this.posY);
        nbt.setFloat(Print.KEY_ZOOM, this.zoom);
        
        NBTUtility.writePrintNodesToNBT(this, nbt);
        NBTUtility.writePrintConnectionsToNBT(this, nbt);
    }
    
    /**
     * Get the idx of the node in the nodes list of the print
     */
    public static int getIdxForNode(Print p, Node n)
    {
        Node n1;
        
        for (int i = 0; i < p.getNodes().size(); ++i)
        {
            n1 = p.getNodes().get(i);
            
            if (n1 == n)
            {
                return i;
            }
        }
        
        Visibilis.error("Could not find index for node!");
        
        return -1;
    }
    
    /**
     * Get the node at the idx in the nodes list of the print
     */
    public static Node getNodeForIdx(Print p, int idx)
    {
        for (int i = 0; i < p.getNodes().size(); ++i)
        {
            if (i == idx)
            {
                return p.getNodes().get(i);
            }
        }
        
        Visibilis.error("Could not find node for index!");
        
        return null;
    }
}
