package de.cas_ual_ty.visibilis.node.field;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.datatype.DataTypeDynamic;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.node.Node;
import net.minecraft.nbt.CompoundNBT;

public class Input<I> extends NodeField<I>
{
    /*
     * Clarifications:
     * 
     * "Node": The node of this node field
     * "Parent": The parent node of the node of this field
     */
    
    public static final String KEY_DATA_ENUM = "dataId";
    public static final String KEY_DATA_DYNAMIC = "data";
    
    /**
     * The output this is connected to.
     */
    protected Output<?> connection;
    
    protected I value = null;
    
    protected boolean mustUseConnection;
    
    public Input(Node node, DataType<I> dataType, String name)
    {
        super(node, dataType, name);
        this.mustUseConnection = !dataType.hasDefaultValue();
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
    
    public I getConvertedValue()
    {
        return this.dataType.convert(this.connection);
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
    public ArrayList<NodeField<?>> getConnectionsList()
    {
        ArrayList<NodeField<?>> list = new ArrayList<>();
        
        if(this.hasConnections())
        {
            list.add(this.connection);
        }
        
        return list;
    }
    
    @Override
    public void clearConnections()
    {
        this.connection = null;
        
        // Connection is cut, reset value
        if(this.value != null)
        {
            this.value = null; //Just to be sure
            
            if(this.dataType instanceof DataTypeDynamic)
            {
                ((DataTypeDynamic<I>)this.dataType).getDefaultValue();
            }
            else if(this.dataType instanceof DataTypeEnum)
            {
                ((DataTypeEnum<I>)this.dataType).getDefaultEnum();
            }
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
        return this.value != null ? this.value : (this.dataType.hasDefaultValue() ? ((I)this.dataType.getDefaultValue()) : null);
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
    public boolean useNBT()
    {
        return this.hasDisplayValue() && (this.dataType instanceof DataTypeEnum || this.dataType instanceof DataTypeDynamic);
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt)
    {
        if(this.dataType instanceof DataTypeEnum)
        {
            DataTypeEnum<I> dt = (DataTypeEnum<I>)this.dataType;
            nbt.putInt(Input.KEY_DATA_ENUM, dt.getEnumIdx(this.getSetValue()));
        }
        else if(this.dataType instanceof DataTypeDynamic)
        {
            DataTypeDynamic<I> dt = (DataTypeDynamic<I>)this.dataType;
            CompoundNBT data = dt.saveToNBT(this.getSetValue());
            if(data != null)
            {
                nbt.put(Input.KEY_DATA_DYNAMIC, data);
            }
        }
        
        super.readFromNBT(nbt);
    }
    
    @Override
    public void readFromNBT(CompoundNBT nbt)
    {
        if(this.dataType instanceof DataTypeEnum)
        {
            DataTypeEnum<I> dt = (DataTypeEnum<I>)this.dataType;
            this.setValue(dt.getEnum(nbt.getInt(Input.KEY_DATA_ENUM)));
        }
        else if(this.dataType instanceof DataTypeDynamic)
        {
            DataTypeDynamic<I> dt = (DataTypeDynamic<I>)this.dataType;
            if(nbt.contains(Input.KEY_DATA_DYNAMIC))
            {
                CompoundNBT data = nbt.getCompound(Input.KEY_DATA_DYNAMIC);
                this.setValue(dt.loadFromNBT(data));
            }
        }
        
        super.writeToNBT(nbt);
    }
}
