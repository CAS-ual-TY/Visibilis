package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.datatype.DataType;

public abstract class NodeField<A>
{
    /*
     * Clarifications:
     * 
     * "Node": The node of this node field
     * "Parent": The parent node of the node of this field
     */
    
    /**
     * The index of this node field in the node.
     */
    private int id;
    
    /**
     * The parent node of this node field.
     */
    public final Node node;
    
    /**
     * The data type of this node field.
     */
    public final DataType dataType;
    
    /**
     * The name of this node field (before translation).
     */
    public final String name;
    
    public NodeField(Node node, DataType dataType, String name)
    {
        this.node = node;
        this.dataType = dataType;
        this.name = name;
        
        if (this.isOutput())
        {
            this.id = this.node.addOutput((Output) this);
        }
        else
        {
            this.id = this.node.addInput((Input) this);
        }
    }
    
    /**
     * Try to connect this node field to another node field.
     * 
     * @param field
     *            The other node field.
     * @return <b>true</b>, if this node field has been connected to the other node field (or the connection was already present).
     */
    protected abstract boolean setConnectionTo(NodeField field);
    
    public abstract boolean isOutput();
    
    public boolean isInput()
    {
        return !this.isOutput();
    }
    
    /**
     * @return The index of this node field in either {@link Node#outputFields} or {@link Node#inputFields}
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * @return The value this node field is currently representing.
     */
    @Nullable
    public abstract A getValue();
    
    /**
     * @return <b>true</b> if this node is connected to another node (or multiple).
     */
    public abstract boolean hasConnections();
    
    /**
     * Retrieve a list of all connections. Changing this list does not do anything to the node field. It can be empty.
     * 
     * @return A list of all node fields this is node field is connected to.
     */
    public abstract ArrayList<NodeField> getConnectionsList();
    
    /**
     * Cut all connections to other fields (but not their connections to this field).
     */
    public abstract void clearConnections();
    
    /**
     * Cut the connection from a field to this field (but not from this field to the given field).
     * 
     * @param field
     *            The node field to cut the connection from.
     */
    public abstract void removeConnectionOneSided(NodeField field);
    
    /**
     * Cut all connections to other fields, and their connections to this field.
     */
    public void cutConnections()
    {
        for (NodeField field : this.getConnectionsList())
        {
            field.removeConnectionOneSided(this);
        }
        
        this.clearConnections();
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldUnlocalizedName(NodeField)
     */
    public String getUnlocalizedName()
    {
        return this.node.getFieldUnlocalizedName(this);
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldUnlocalizedDesc(NodeField)
     */
    public String getUnlocalizedDesc()
    {
        return this.node.getFieldUnlocalizedDesc(this);
    }
    
    public static void connect(Output out, Input in)
    {
        // Inputs can only be connected once...
        if (in.connection != null && in.connection != out)
        {
            // ... so remove the input from the current output it is connected to (if there is any)
            in.connection.removeConnectionOneSided(in);
        }
        
        out.setConnectionTo(in);
        in.setConnectionTo(out);
    }
    
    /**
     * Returns false if both are Inputs/Outputs, otherwise returns {@link #canConnect(Output, Input, boolean)} ignoring present connections (on true).
     */
    public static boolean canConnect(NodeField n1, NodeField n2)
    {
        return (n1.isOutput() != n2.isOutput()) && (n1.isOutput() ? NodeField.canConnect((Output) n1, (Input) n2, true) : NodeField.canConnect((Output) n2, (Input) n1, true));
    }
    
    /**
     * Check if the given Input and Output can be connected to each other.
     * 
     * @param ignorePresentConnection
     *            If <b>false</b> then it will immediately return <b>false</b> if the Input already has a connection, if <b>true</b> then it will ignore a present connection of the input
     */
    public static boolean canConnect(Output out, Input in, boolean ignorePresentConnection)
    {
        return out.node != in.node & in.dataType.canConvert(out.dataType) && (!ignorePresentConnection ? (in.connection == null) : true);
    }
    
    public static boolean tryConnect(NodeField n1, NodeField n2)
    {
        if (n1 instanceof Output && n2 instanceof Input)
        {
            return NodeField.tryConnect((Output) n1, (Input) n2, true);
        }
        else if (n2 instanceof Output && n1 instanceof Input)
        {
            return NodeField.tryConnect((Output) n2, (Input) n1, true);
        }
        
        return false;
    }
    
    public static boolean tryConnect(Output out, Input in, boolean ignorePresentConnection)
    {
        if (NodeField.canConnect(out, in))
        {
            NodeField.connect(out, in);
            return true;
        }
        
        return false;
    }
}
