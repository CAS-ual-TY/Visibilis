package de.cas_ual_ty.visibilis.datatype;

import java.util.HashMap;
import java.util.Map;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.converter.AnyString;
import de.cas_ual_ty.visibilis.datatype.converter.IntegerFloat;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import net.minecraft.nbt.CompoundNBT;

public class DataType<A>
{
    public static final float[] COLOR_TEXT_WHITE = new float[] { 1F, 1F, 1F };
    public static final float[] COLOR_TEXT_BLACK = new float[] { 0F, 0F, 0F };
    
    public static final float[] COLOR_DEFAULT_GREY = new float[] { 0.5F, 0.5F, 0.5F };
    
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
    
    @SuppressWarnings("rawtypes")
    public static final DataType EXEC = new DataType("exec", new float[] { 1F, 0F, 0F });
    
    public static final DataTypeDynamic<Integer> INTEGER = (DataTypeDynamic<Integer>) new DataTypeDynamic<Integer>("integer", new float[] { 0.5F, 1F, 0F }, 1)
    {
        @Override
        public boolean equals(Integer obj1, Integer obj2)
        {
            return obj1.intValue() == obj2.intValue();
        }
        
        @Override
        public boolean canParseString(String s)
        {
            if (s.equals("-"))
            {
                return true;
            }
            
            try
            {
                this.stringToValue(s);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        
        @Override
        public Integer stringToValue(String s)
        {
            if (s.equals("-"))
            {
                return 0;
            }
            return Integer.parseInt(s);
        }
        
        @Override
        public Integer loadFromNBT(CompoundNBT nbt)
        {
            return nbt.getInt(DataType.KEY_DATA);
        }
        
        @Override
        public CompoundNBT saveToNBT(Integer data)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt(DataType.KEY_DATA, data.intValue());
            return nbt;
        }
    }.setBlackText();
    
    public static final DataTypeDynamic<Float> FLOAT = (DataTypeDynamic<Float>) new DataTypeDynamic<Float>("float", new float[] { 1F, 1F, 0F }, 1.0F)
    {
        @Override
        public boolean equals(Float obj1, Float obj2)
        {
            return obj1.floatValue() == obj2.floatValue();
        }
        
        @Override
        public boolean canParseString(String s)
        {
            if (s.equals("-"))
            {
                return true;
            }
            
            try
            {
                this.stringToValue(s);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        
        @Override
        public Float stringToValue(String s)
        {
            if (s.equals("-"))
            {
                return 0F;
            }
            return Float.parseFloat(s);
        }
        
        @Override
        public Float loadFromNBT(CompoundNBT nbt)
        {
            return nbt.getFloat(DataType.KEY_DATA);
        }
        
        @Override
        public CompoundNBT saveToNBT(Float data)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putFloat(DataType.KEY_DATA, data.floatValue());
            return nbt;
        }
    }.setBlackText();
    
    public static final DataTypeEnum<Boolean> BOOLEAN = new DataTypeEnum<Boolean>("boolean", new float[] { 1F, 0F, 1F })
    {
        @Override
        public boolean equals(Boolean obj1, Boolean obj2)
        {
            return obj1.booleanValue() == obj2.booleanValue();
        }
    }.addEnum(false).addEnum(true);
    
    public static final DataTypeDynamic<String> STRING = (DataTypeDynamic<String>) new DataTypeDynamic<String>("string", new float[] { 1F, 1F, 1F }, "text")
    {
        @Override
        public boolean canParseString(String s)
        {
            return true; // TODO Maybe not everything should be allowed? eg \". Problems when loading from NBT?
        }
        
        @Override
        public String stringToValue(String s)
        {
            return s;
        }
        
        @Override
        public String loadFromNBT(CompoundNBT nbt)
        {
            return nbt.getString(DataType.KEY_DATA);
        }
        
        @Override
        public CompoundNBT saveToNBT(String data)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(DataType.KEY_DATA, data);
            return nbt;
        }
    }.setBlackText();
    
    static
    {
        DataType.FLOAT.registerConverter(DataType.INTEGER, new IntegerFloat()); // As I dont know how this will be used in the future, I rather add this in.
        
        DataType.STRING.registerConverter(DataType.INTEGER, new AnyString());
        DataType.STRING.registerConverter(DataType.FLOAT, new AnyString());
        DataType.STRING.registerConverter(DataType.BOOLEAN, new AnyString());
    }
    
    /**
     * All registered converters
     */
    protected HashMap<DataType<?>, Converter> converters;
    
    /**
     * Unique id. Used for translation
     */
    public final String id;
    
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
    
    public DataType(String id)
    {
        this(id, DataType.COLOR_DEFAULT_GREY);
    }
    
    public DataType(String id, float[] color)
    {
        this.id = id;
        this.converters = new HashMap<>();
        this.color = color;
        this.textColor = DataType.COLOR_TEXT_WHITE;
        this.defaultValue = null;
        
        if (DataType.DATA_TYPES_LIST.containsKey(id))
        {
            Visibilis.error("Data type \"" + id + "\" already exists!");
        }
        
        DataType.DATA_TYPES_LIST.put(id, this);
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
    public /* final */ DataType<A> registerConverter(DataType<?> from, Converter converter)
    {
        if (this == DataType.EXEC || from == DataType.EXEC)
        {
            Visibilis.error("EXEC is not convertible!");
            return this;
        }
        
        converter.setFromTo(from, this);
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
    public /* final */ DataType<A> registerGenericConverter(DataType<?> from)
    {
        if (this == DataType.EXEC || from == DataType.EXEC)
        {
            return this;
        }
        
        Converter converter = new Converter().setFromTo(from, this);
        
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
    public A convert(NodeField<?> from)
    {
        return this.convert(from.dataType, from.getValue());
    }
    
    /**
     * Converts the value which represents the given data type to this data type; {@link #canConvert(DataType)} for the given data type must be true otherwise NullPointer is thrown.
     */
    public A convert(DataType<?> from, Object value)
    {
        return this.converters.get(from).<A> convert(value);
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
    
    public DataType<A> setTextColor(float[] color)
    {
        this.textColor = color;
        return this;
    }
    
    public DataType<A> setBlackText()
    {
        return this.setTextColor(DataType.COLOR_TEXT_BLACK);
    }
}
