package de.cas_ual_ty.visibilis.datatype;

import java.util.HashMap;
import java.util.Map;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.converter.IConverter;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DataType<A> extends ForgeRegistryEntry<DataType<?>>
{
    public static final Map<String, DataType<?>> DATA_TYPES_LIST = new HashMap<>();
    
    public static final String KEY_DATA = "data";
    
    /*
     * Explanation for all this overriding:
     * 
     * There are 4 kinds of data types:
     * - 1. DataType without a default value
     * - 2. DataType with a default value
     * - 3. DataTypeDynamic
     * - 4. DataTypeEnum
     * 
     * They have the following advantages (same order as above):
     * - 1. None
     * - 2. Unconnected inputs now use the default value (and dont require a connection anymore)
     * - 3. Point 2 and strings can now be parsed to a value
     * - 4. Point 2 and there is a list of possible values available to choose from
     * 
     * Point 3 and 4 allow a box to be shown in Gui to either
     * - Show an input field to type a value
     * - Show a drop down for a value to choose from
     * 
     * For any non primitive data type, #valueToString(A) should also be overridden in the data type class (or #toString() in the passed generic class (A)).
     * You can choose yourself which of these 2 options fit your data type better. But if there are too many possibilities, then the Dynamic one should be better.
     * 
     * This is all not required for a data type anyways. Sometimes it is definitely better NOT to implement this with very advanced types (eg. a list type).
     */
    
    /**
     * All registered converters
     */
    protected HashMap<DataType<?>, IConverter<?, A>> converters;
    
    /**
     * Color for rendering - background of the text. This should be colored, its the representation of this data type
     */
    protected float[] color;
    
    /**
     * Color for rendering - text itself. This should be a grey tone, ideally just black or white.
     */
    protected float[] textColor;
    
    /**
     * The default value for unconnected inputs
     */
    protected A defaultValue;
    
    protected ArrayFactory<A> arrayFactory;
    
    public DataType(ArrayFactory<A> arrayFactory)
    {
        this.converters = new HashMap<>();
        this.defaultValue = null;
        this.arrayFactory = arrayFactory;
        this.color = VUtility.COLOR_DEFAULT_GREY;
        this.textColor = VUtility.COLOR_DEFAULT_WHITE;
    }
    
    public A[] createArray(int length)
    {
        return this.arrayFactory.createArray(length);
    }
    
    public boolean equals(A obj1, A obj2)
    {
        return obj1.equals(obj2);
    }
    
    /**
     * Register a new converter and a new data type this type accepts.
     * 
     * @param from
     *            The data type this data type now accepts
     * @param converter
     *            The converter to set how data is converted
     * @return This for chaining
     */
    public <F> DataType<A> registerConverter(DataType<F> from, IConverter<F, A> converter)
    {
        if(this == VDataTypes.EXEC || from == VDataTypes.EXEC)
        {
            Visibilis.error("EXEC is not convertible!");
            return this;
        }
        
        this.converters.put(from, converter);
        return this;
    }
    
    /**
     * Register a generic converter where the from type can just be casted to this type
     * 
     * @param from
     *            The data type this data type now accepts
     * @param converter
     *            The converter to set how data is converted
     * @return This for chaining
     */
    public <F> DataType<A> registerGenericConverter(DataType<F> from)
    {
        if(this == VDataTypes.EXEC || from == VDataTypes.EXEC)
        {
            return this;
        }
        
        IConverter<F, A> converter = (f) -> VUtility.cast(f);
        
        this.converters.put(from, converter);
        return this;
    }
    
    /**
     * @return <b>true</b> if any data of the given data type can be converted to this data type
     */
    public boolean canConvert(DataType<?> from)
    {
        return from == this || this.converters.containsKey(from);
    }
    
    /**
     * Converts the value of this node field to this data type; {@link #canConvert(DataType)} for the node field's data type must be true otherwise NullPointer is thrown.
     */
    public <F> A convert(NodeField<F> from)
    {
        return this.convert(from.getDataType(), from.getValue());
    }
    
    /**
     * Converts the value which represents the given data type to this data type; {@link #canConvert(DataType)} for the given data type must be true otherwise NullPointer is thrown.
     */
    public <F> A convert(DataType<F> from, F value)
    {
        return from == this ? VUtility.cast(value) : (VUtility.<IConverter<F, A>, IConverter<?, A>>cast(this.converters.get(from))).convert(value);
    }
    
    /**
     * Set the default value. This default value will be used for inputs without connection.
     */
    public DataType<A> setDefaultValue(A value)
    {
        this.defaultValue = value;
        return this;
    }
    
    /**
     * Returns the default value. This default value will be used for inputs without connection.
     * 
     * @see #setDefaultValue(Object)
     */
    public A getDefaultValue()
    {
        return this.defaultValue;
    }
    
    /**
     * Gets a string representation of the default value.
     * 
     * @see #getDefaultValue()
     */
    public String getDefaultValueString()
    {
        return this.valueToString(this.getDefaultValue());
    }
    
    /**
     * Returns <b>true</b> if this either has a set default value or is an enum.
     * 
     * @see #getDefaultValue()
     * @see #getEnum(int)
     */
    public boolean hasDefaultValue()
    {
        return this.getDefaultValue() != null;
    }
    
    public String valueToString(A value)
    {
        return value.toString();
    }
    
    /**
     * Get the color of this data type, for connections and node field dots.
     * 
     * @return
     */
    public float[] getColor()
    {
        return this.color;
    }
    
    /**
     * Color of the text rendered on top of rectangles of {@link #getColor()} color. So it should be distinguishable
     */
    public float[] getTextColor()
    {
        return this.textColor;
    }
    
    public DataType<A> setColor(float[] color)
    {
        this.color = color;
        return this;
    }
    
    public DataType<A> setTextColor(float[] textColor)
    {
        this.textColor = textColor;
        return this;
    }
    
    public boolean isSerializable()
    {
        return false;
    }
    
    public A loadFromNBT(CompoundNBT nbt)
    {
        return this.readFromNBT(nbt, DataType.KEY_DATA);
    }
    
    public A readFromNBT(CompoundNBT nbt, String key)
    {
        return null;
    }
    
    public CompoundNBT saveToNBT(A data)
    {
        CompoundNBT nbt = new CompoundNBT();
        this.writeToNBT(nbt, DataType.KEY_DATA, data);
        return nbt;
    }
    
    public void writeToNBT(CompoundNBT nbt, String key, A value)
    {
        
    }
    
    public static interface ArrayFactory<A>
    {
        A[] createArray(int length);
    }
}
