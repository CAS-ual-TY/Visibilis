package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.datatype.DataType;

public class Input<A> extends NodeField<A>
{
    /*
     * Clarifications: "Node": The node of this node field "Parent": The parent node of the node of this field
     */
    
    /**
     * The output this is connected to.
     */
    protected Output connection;
    
    protected A value = null;
    
    public Input(int id, Node node, DataType dataType, String name)
    {
        super(id, node, dataType, name);
    }
    
    /**
     * @return The output this is connected to (or null).
     */
    public Output getConnection()
    {
        return this.connection;
    }
    
    @Override
    protected boolean setConnectionTo(NodeField field)
    {
        if (field instanceof Output)
        {
            Output output = (Output) field;
            
            this.connection = output;
            return true;
        }
        
        return false;
    }
    
    /**
     * The first one that is true (top to bottom):</br>
     * If this input is connected -> value of connected output.</br>
     * If this input has a default value -> this input's default value.</br>
     * If this input's data type has a default value -> this input's data type's default value.
     * @return The value this node field is currently representing.
     * @see NodeField#getValue()
     */
    @Override
    public A getValue()
    {
        return this.hasConnections() ? (A) this.connection.getValue() : (this.value != null ? this.value : (this.dataType.hasDefaultValue() ? ((A) this.dataType.getDefaultValue()) : null));
    }
    
    @Override
    public boolean hasConnections()
    {
        return this.connection != null;
    }
    
    @Override
    public boolean isOutput()
    {
        return false;
    }
    
    @Override
    public ArrayList<NodeField> getConnectionsList()
    {
        ArrayList<NodeField> list = new ArrayList<NodeField>();
        
        if (this.hasConnections())
        {
            list.add(this.connection);
        }
        
        return list;
    }
    
    @Override
    public void clearConnections()
    {
        this.connection = null;
    }
    
    @Override
    public void removeConnectionOneSided(NodeField field)
    {
        if (this.connection == field)
        {
            this.connection = null;
        }
    }
    
    /**
     * Set the value that is used if {@link #hasConnections()} is <b>false</b>
     */
    public Input setValue(A value)
    {
        this.value = value;
        return this;
    }
}
