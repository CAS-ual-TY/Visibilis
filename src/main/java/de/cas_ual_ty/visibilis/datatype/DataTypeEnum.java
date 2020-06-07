package de.cas_ual_ty.visibilis.datatype;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.field.Input;
import net.minecraft.nbt.CompoundNBT;

public class DataTypeEnum<A> extends DataType<A>
{
    /**
     * List of all possible values this data type can have. Used for enums and eg. booleans.
     */
    protected ArrayList<A> enums = null;
    
    public DataTypeEnum(ArrayFactory<A> arrayFactory)
    {
        this(DataType.COLOR_DEFAULT_GREY, DataType.COLOR_TEXT_WHITE, arrayFactory);
    }
    
    public DataTypeEnum(float[] color, float[] textColor, ArrayFactory<A> arrayFactory)
    {
        super(color, textColor, arrayFactory);
        this.enums = new ArrayList<>();
    }
    
    @Override
    public boolean equals(A obj1, A obj2)
    {
        return this.getEnumIdx(obj1) == this.getEnumIdx(obj2);
    }
    
    @Override
    public DataType<A> setDefaultValue(A value)
    {
        if(!this.enums.contains(value))
        {
            this.enums.add(value);
        }
        
        return super.setDefaultValue(value);
    }
    
    @Override
    public A getDefaultValue()
    {
        if(this.defaultValue == null)
        {
            this.defaultValue = this.getDefaultEnum();
        }
        
        return super.getDefaultValue();
    }
    
    /**
     * Adds a possible value to this data type.
     */
    public DataTypeEnum<A> addEnum(A value)
    {
        this.enums.add(value);
        return this;
    }
    
    /**
     * Gets the (enum) value at the given index.
     */
    public A getEnum(int idx)
    {
        return this.enums.get(idx);
    }
    
    /**
     * {@link #getEnum(int)} at idx 0
     */
    public A getDefaultEnum()
    {
        return this.getEnum(0);
    }
    
    /**
     * Get the idx for the given value
     */
    public int getEnumIdx(A value)
    {
        for(int i = 0; i < this.enums.size(); ++i)
        {
            if(this.enums.get(i) == value)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public int getEnumSize()
    {
        return this.enums.size();
    }
    
    @Override
    public boolean isSerializable()
    {
        return true;
    }
    
    @Override
    public A readFromNBT(CompoundNBT nbt, String key)
    {
        return this.getEnum(nbt.getInt(key));
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt, String key, A value)
    {
        nbt.putInt(key, this.getEnumIdx(value));
    }
    
    public static <A> void setEnum(Input<A> input, int _enum)
    {
        input.setValue(((DataTypeEnum<A>)input.getDataType()).getEnum(_enum));
    }
}
