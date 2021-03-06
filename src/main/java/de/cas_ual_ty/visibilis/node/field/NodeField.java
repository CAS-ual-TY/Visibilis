package de.cas_ual_ty.visibilis.node.field;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeField<B>
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
    private final Node node;
    
    /**
     * The data type of this node field.
     */
    private final DataType<B> dataType;
    
    /**
     * The name of this node field (before translation).
     */
    private Function<NodeField<B>, String> name;
    
    private boolean forceDynamic;
    
    public NodeField(Node node, DataType<B> dataType, Function<NodeField<B>, String> name)
    {
        this.node = node;
        this.dataType = dataType;
        this.name = name;
        this.forceDynamic = false;
    }
    
    public NodeField(Node node, DataType<B> dataType, String name)
    {
        this(node, dataType, (field) -> name);
    }
    
    public Node getNode()
    {
        return this.node;
    }
    
    public DataType<B> getDataType()
    {
        return this.dataType;
    }
    
    public String getName()
    {
        return this.name.apply(this);
    }
    
    public boolean doesForceDynamic()
    {
        return this.forceDynamic;
    }
    
    public NodeField<B> setForceDynamic()
    {
        this.forceDynamic = true;
        return this;
    }
    
    /**
     * Try to connect this node field to another node field.
     * 
     * @param field
     *            The other node field.
     * @return <b>true</b>, if this node field has been connected to the other node field (or the connection was already present).
     */
    protected abstract boolean setConnectionTo(NodeField<?> field);
    
    public abstract boolean isOutput();
    
    public abstract boolean isInput();
    
    public void recalculateId()
    {
        if(this.isOutput())
        {
            this.id = this.getNode().getOutputId((Output<B>)this);
        }
        else
        {
            this.id = this.getNode().getInputId((Input<B>)this);
        }
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
    public abstract B getValue();
    
    public abstract void onConnect();
    
    public abstract void onDisconnect();
    
    /**
     * @return <b>true</b> if this node is connected to another node (or multiple).
     */
    public abstract boolean hasConnections();
    
    /**
     * Retrieve a list of all connections. Changing this list does not do anything to the node field. It can be empty.
     * 
     * @return A list of all node fields this is node field is connected to.
     */
    public abstract List<NodeField<?>> getConnectionsList();
    
    /**
     * Cut all connections to other fields (but not their connections to this field).
     */
    protected abstract void clearConnections();
    
    /**
     * Cut the connection from a field to this field (but not from this field to the given field).
     * 
     * @param field
     *            The node field to cut the connection from.
     */
    public abstract void removeConnectionOneSided(NodeField<?> field);
    
    /**
     * Cut all connections to other fields, and their connections to this field.
     */
    public void cutConnections()
    {
        for(NodeField<?> field : this.getConnectionsList())
        {
            field.removeConnectionOneSided(this);
        }
        
        this.clearConnections();
        
        this.onDisconnect();
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldUnlocalizedName(NodeField)
     */
    public String getUnlocalizedName()
    {
        return this.getNode().getFieldUnlocalizedName(this);
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldUnlocalizedDesc(NodeField)
     */
    public String getUnlocalizedDesc()
    {
        return this.getNode().getFieldUnlocalizedDesc(this);
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldName(NodeField)
     */
    public String getNameTranslated()
    {
        return this.getNode().getFieldName(this);
    }
    
    /**
     * @see de.cas_ual_ty.visibilis.node.Node#getFieldDesc(NodeField)
     */
    public String getDesc()
    {
        return this.getNode().getFieldDesc(this);
    }
    
    /**
     * When set to true, {@link #readFromNBT(CompoundNBT)} and {@link #writeToNBT(CompoundNBT)} will be called
     */
    public boolean useNBT()
    {
        return false;
    }
    
    public void readFromNBT(CompoundNBT nbt)
    {
    }
    
    public void writeToNBT(CompoundNBT nbt)
    {
    }
    
    public static void connect(NodeField<?> n1, NodeField<?> n2)
    {
        if(n1.isOutput() != n2.isOutput())
        {
            if(n1.isOutput())
            {
                NodeField.connect((Output<?>)n1, (Input<?>)n2);
            }
            else
            {
                NodeField.connect((Output<?>)n2, (Input<?>)n1);
            }
        }
    }
    
    public static final <O, I> void connect(Output<O> out, Input<I> in)
    {
        // Inputs can only be connected once...
        if(in.connection != null && in.connection != out)
        {
            // ... so remove the input from the current output it is connected to (if there is any)
            in.connection.removeConnectionOneSided(in);
        }
        
        out.setConnectionTo(in);
        in.setConnectionTo(out);
        
        out.onConnect();
        in.onConnect();
    }
    
    /**
     * Returns false if both are Inputs/Outputs, otherwise returns {@link #canConnect(Output, Input, boolean)} ignoring present connections (on true).
     */
    public static boolean canConnect(NodeField<?> n1, NodeField<?> n2)
    {
        return (n1.isOutput() != n2.isOutput()) && (n1.isOutput() ? NodeField.canConnect((Output<?>)n1, (Input<?>)n2, true) : NodeField.canConnect((Output<?>)n2, (Input<?>)n1, true));
    }
    
    /**
     * Check if the given Input and Output can be connected to each other.
     * 
     * @param ignorePresentConnection
     *            If <b>false</b> then it will immediately return <b>false</b> if the Input already has a connection, if <b>true</b> then it will ignore a present connection of the input
     */
    public static boolean canConnect(Output<?> out, Input<?> in, boolean ignorePresentConnection)
    {
        return out.getNode() != in.getNode() & in.getDataType().canConvert(out.getDataType()) && (!ignorePresentConnection ? (in.connection == null) : true);
    }
    
    public static boolean tryConnect(NodeField<?> n1, NodeField<?> n2)
    {
        if(n1 instanceof Output && n2 instanceof Input)
        {
            return NodeField.tryConnect((Output<?>)n1, (Input<?>)n2, true);
        }
        else if(n2 instanceof Output && n1 instanceof Input)
        {
            return NodeField.tryConnect((Output<?>)n2, (Input<?>)n1, true);
        }
        
        return false;
    }
    
    public static boolean tryConnect(Output<?> out, Input<?> in, boolean ignorePresentConnection)
    {
        if(NodeField.canConnect(out, in))
        {
            NodeField.connect(out, in);
            return true;
        }
        
        return false;
    }
}
