package de.cas_ual_ty.visibilis.node.field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.nbt.CompoundNBT;

public class Input<I> extends NodeField<I>
{
    /*
     * Clarifications:
     * 
     * "Node": The node of this node field
     * "Parent": The parent node of the node of this field
     */
    
    public static final String KEY_DATA = "data";
    
    /**
     * The output this is connected to.
     */
    protected Output<?> connection;
    
    protected I value = null;
    
    protected boolean mustUseConnection;
    
    public Input(Node node, DataType<I> dataType, Function<NodeField<I>, String> name)
    {
        super(node, dataType, name);
        this.mustUseConnection = !this.getDataType().hasDefaultValue();
    }
    
    public Input(Node node, DataType<I> dataType, String name)
    {
        this(node, dataType, (field) -> name);
    }
    
    @Override
    public Input<I> setForceDynamic()
    {
        super.setForceDynamic();
        
        if(this.getDataType() != VDataTypes.EXEC)
        {
            this.setMustUseConnection();
        }
        
        return this;
    }
    
    /**
     * @return The output this is connected to (or null).
     */
    public Output<?> getConnection()
    {
        return this.connection;
    }
    
    @Override
    protected boolean setConnectionTo(NodeField<?> field)
    {
        if(field instanceof Output)
        {
            Output<?> output = (Output<?>)field;
            
            this.connection = output;
            return true;
        }
        
        return false;
    }
    
    @Override
    public final boolean isOutput()
    {
        return false;
    }
    
    @Override
    public final boolean isInput()
    {
        return true;
    }
    
    /**
     * The first one that is true (top to bottom):</br>
     * If this input is connected -> value of connected output.</br>
     * If this input has a default value -> this input's default value.</br>
     * If this input's data type has a default value -> this input's data type's default value.
     * 
     * @return The value this node field is currently representing.
     * @see NodeField#getValue()
     */
    @Override
    public I getValue()
    {
        return this.hasConnections() ? (I)this.getConvertedValue() : this.getSetValue();
    }
    
    protected I getConvertedValue()
    {
        return this.getDataType().convert(this.connection);
    }
    
    @Override
    public boolean hasConnections()
    {
        return this.connection != null;
    }
    
    @Override
    public List<NodeField<?>> getConnectionsList()
    {
        List<NodeField<?>> list = new ArrayList<>(1);
        
        if(this.hasConnections())
        {
            list.add(this.connection);
        }
        
        return list;
    }
    
    @Override
    protected void clearConnections()
    {
        this.connection = null;
        
        // Connection is cut, reset value
        if(this.value != null)
        {
            this.value = this.getDataType().getDefaultValue();
        }
    }
    
    @Override
    public void removeConnectionOneSided(NodeField<?> field)
    {
        if(this.connection == field)
        {
            this.connection = null;
        }
    }
    
    /**
     * The first one that is true (top to bottom):</br>
     * If this input has a default value -> this input's default value.</br>
     * If this input's data type has a default value -> this input's data type's default value.
     * 
     * @return The value this node field is currently representing, ignoring connections.
     * @see #getValue()
     */
    public I getSetValue()
    {
        return this.value != null ? this.value : (this.getDataType().hasDefaultValue() ? ((I)this.getDataType().getDefaultValue()) : null);
    }
    
    /**
     * @return <b>true</b> if this input is representing a fixed, immediate value (and is unconnected).
     */
    public boolean hasDisplayValue()
    {
        return !this.hasConnections() && this.getSetValue() != null;
    }
    
    /**
     * Set the value that is used if {@link #hasConnections()} is <b>false</b>
     */
    public Input<I> setValue(I value)
    {
        this.value = value;
        this.mustUseConnection = false;
        return this;
    }
    
    public boolean getMustUseConnections()
    {
        return this.getValue() == null || this.mustUseConnection;
    }
    
    /**
     * By calling this, {@link #getValue()} will only return a value from the connection.
     * A connection is required for this input.
     */
    public Input<I> setMustUseConnection()
    {
        this.mustUseConnection = true;
        return this;
    }
    
    @Override
    public void onConnect()
    {
        this.getNode().updateDynamic();
        this.getNode().onInputConnect(this);
    }
    
    @Override
    public void onDisconnect()
    {
        this.getNode().updateDynamic();
        this.getNode().onInputDisconnect(this);
    }
    
    @Override
    public boolean useNBT()
    {
        return this.hasDisplayValue() && this.getDataType().isSerializable();
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt)
    {
        if(this.getDataType().isSerializable())
        {
            this.getDataType().writeToNBT(nbt, Input.KEY_DATA, this.getSetValue());
        }
        
        super.readFromNBT(nbt);
    }
    
    @Override
    public void readFromNBT(CompoundNBT nbt)
    {
        if(this.getDataType().isSerializable())
        {
            this.setValue(this.getDataType().readFromNBT(nbt, Input.KEY_DATA));
        }
        
        super.writeToNBT(nbt);
    }
}
