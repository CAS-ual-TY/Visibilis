package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeEvent;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;

public class Print
{
    /*
     * Explanation: The Print type is just my own take and implementation of all the Nodes.
     */
    
    // NBT Keys
    public static final String KEY_POS_X = "posX";
    public static final String KEY_POS_Y = "posY";
    public static final String KEY_ZOOM = "zoom";
    
    /**
     * Where the user currently shifted the print in the GUI. Saved so that they start off again where they last left
     */
    private int posX;
    private int posY;
    
    /**
     * How much the user currently zoomed in the GUI. Saved so that they start off again where they last left
     */
    private float zoom;
    
    /**
     * All nodes in this print (including events).
     */
    protected final ArrayList<Node> nodes;
    
    /**
     * All event nodes in this print
     */
    protected final ArrayList<NodeEvent> events;
    
    public Print()
    {
        this.nodes = new ArrayList<>();
        this.events = new ArrayList<>();
        this.reset();
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
        if(this.containsNode(node))
        {
            // Remove node from the list to add it on top again
            this.removeNodeKeepConnections(node);
        }
        
        this.nodes.add(node);
        
        if(node instanceof NodeEvent)
        {
            this.events.add((NodeEvent)node);
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
        if(node instanceof NodeEvent)
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
    
    /**
     * @return The list of events (no clone or copy).
     */
    public ArrayList<NodeEvent> getEvents()
    {
        return this.events;
    }
    
    public boolean executeEvent(String modId, String eventType, CommandSource sender)
    {
        return this.executeEvent(modId + ":" + eventType, sender);
    }
    
    public boolean executeEvent(String eventType, CommandSource sender)
    {
        return this.executeEvent(eventType, (print) -> new DataProvider(print, sender));
    }
    
    public boolean executeEvent(String modId, String eventType, Function<Print, DataProvider> data)
    {
        return this.executeEvent(modId + ":" + eventType, data);
    }
    
    public boolean executeEvent(String eventType, Function<Print, DataProvider> data)
    {
        return this.executeEvent(eventType, data.apply(this));
    }
    
    protected boolean executeEvent(String eventType, DataProvider data)
    {
        NodeEvent event;
        
        // Start from back of list (= on top of gui) to front of list (= on bottom of gui)
        for(int i = this.events.size() - 1; i >= 0; --i)
        {
            event = this.events.get(i);
            
            if(event.eventType.equals(eventType))
            {
                return this.execute(event, data);
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
    public boolean execute(Node node, DataProvider data)
    {
        if(VUtility.isShutdown())
        {
            return false;
        }
        
        boolean ret = this.exec(node, data);
        
        for(Node n : this.nodes)
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
    protected boolean exec(Node node, DataProvider context)
    {
        // If the node can not be calculated, abort
        if(!node.calculate(context))
        {
            return false;
        }
        
        Output<?> out;
        Input<?> in;
        Node next;
        int i = 0;
        
        int j;
        Output<?> out2;
        Input<?> in2;
        
        /*
         * Now to explain the following segment and loop... You might be asking: "Why a loop? Why not just a single connection?" And the answer is in the question: Loops! This way, you can make nodes which can actually loop. Because for every exec node it will keep looping through, until no further sub node can be found.
         */
        
        // Loop through all outputs of the exec type
        while((out = node.getOutExec(i++)) != null)
        {
            if(!out.hasConnections())
            {
                continue;
            }
            
            for(j = 0; j < node.getInputAmt(); ++j)
            {
                in2 = node.getInput(j);
                
                if(in2.doesTriggerRecalculation())
                {
                    node.triggerParentRecalculation(in2);
                }
            }
            
            for(j = 0; j < node.getOutputAmt(); ++j)
            {
                out2 = node.getOutput(j);
                
                if(out2.doesTriggerRecalculation())
                {
                    node.triggerChildRecalculation(out2);
                }
            }
            
            // Get the connected input of the next node
            in = out.getConnections().get(0);
            
            if(in != null)
            {
                // Get the node of the input
                next = in.getNode();
                
                if(next != null)
                {
                    // Check the the sub node has been successfully calculated
                    if(!this.exec(next, context))
                    {
                        // Abort if false
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Read from NBT.
     */
    public void readFromNBT(CompoundNBT nbt)
    {
        this.setPosX(nbt.getInt(Print.KEY_POS_X));
        this.setPosY(nbt.getInt(Print.KEY_POS_Y));
        this.setZoom(nbt.getFloat(Print.KEY_ZOOM));
        
        VNBTUtility.readPrintNodesFromNBT(this, nbt);
        VNBTUtility.readPrintConnectionsFromNBT(this, nbt);
    }
    
    /**
     * Write to NBT.
     */
    public void writeToNBT(CompoundNBT nbt)
    {
        nbt.putInt(Print.KEY_POS_X, this.getPosX());
        nbt.putInt(Print.KEY_POS_Y, this.getPosY());
        nbt.putFloat(Print.KEY_ZOOM, this.getZoom());
        
        VNBTUtility.writePrintNodesToNBT(this, nbt);
        VNBTUtility.writePrintConnectionsToNBT(this, nbt);
    }
    
    /**
     * Get the idx of the node in the nodes list of the print
     */
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
    
    /**
     * Get the node at the idx in the nodes list of the print
     */
    public static Node getNodeForIdx(Print p, int idx)
    {
        if(idx < p.getNodes().size())
        {
            return p.getNodes().get(idx);
        }
        
        Visibilis.error("Could not find node for index!");
        
        return null;
    }
    
    /**
     * Returns a print clone, including all Nodes cloned.
     */
    @Override
    public Print clone()
    {
        //Yes very lazy I know :P But atleast we have a solution for now
        return VNBTUtility.loadPrintFromNBT(VNBTUtility.savePrintToNBT(this));
    }
    
    public void reset()
    {
        this.setPosX(0);
        this.setPosY(0);
        this.nodes.clear();
        this.events.clear();
        this.zoom = 0.5F;
    }
    
    public void overrideFromNBT(CompoundNBT nbt)
    {
        this.reset();
        VNBTUtility.readPrintFromNBT(this, nbt);
    }
    
    public int getPosX()
    {
        return this.posX;
    }
    
    public void setPosX(int posX)
    {
        this.posX = posX;
    }
    
    public void setPosX(double posX)
    {
        this.posX = MathHelper.floor(posX);
    }
    
    public int getPosY()
    {
        return this.posY;
    }
    
    public void setPosY(int posY)
    {
        this.posY = posY;
    }
    
    public void setPosY(double posY)
    {
        this.posY = MathHelper.floor(posY);
    }
    
    public float getZoom()
    {
        return this.zoom;
    }
    
    public void setZoom(float zoom)
    {
        this.zoom = zoom;
    }
}
