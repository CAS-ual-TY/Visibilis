package de.cas_ual_ty.visibilis.datatype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.converter.FloatNumber;
import de.cas_ual_ty.visibilis.datatype.converter.IntegerNumber;
import de.cas_ual_ty.visibilis.datatype.converter.NumberFloat;
import de.cas_ual_ty.visibilis.datatype.converter.NumberInteger;
import de.cas_ual_ty.visibilis.node.NodeField;

public class DataType<A>
{
    public static final float[] COLOR_TEXT_WHITE = new float[] { 1F, 1F, 1F };
    public static final float[] COLOR_TEXT_BLACK = new float[] { 0F, 0F, 0F };
    
    public static final Map<String, DataType> DATA_TYPES_LIST = new HashMap<String, DataType>();
    
    /*
     * Scroll down for more comments and explanations to why I am doing all this overriding and chaining up here.
     */
    
    public static final DataType EXEC = new DataType("exec", new float[] { 1F, 0F, 0F });
    
    public static final DataType<Float> FLOAT = new DataType<Float>("float", new float[] { 1F, 0.5F, 0F })
    {
        @Override
        public boolean acceptStrings()
        {
            return true;
        }
        
        @Override
        public String valueToString(Float value)
        {
            return value.toString();
        }
        
        @Override
        public boolean canParseString(String s)
        {
            try
            {
                Float.valueOf(s);
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
            return Float.valueOf(s);
        }
    }.setDefaultValue(1F).setBlackText();
    
    public static final DataType<Integer> INTEGER = new DataType<Integer>("integer", new float[] { 0.5F, 1F, 0F })
    {
        @Override
        public boolean acceptStrings()
        {
            return true;
        }
        
        @Override
        public boolean canParseString(String s)
        {
            try
            {
                Integer.valueOf(s);
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
            return Integer.valueOf(s);
        }
    }.setDefaultValue(1).setBlackText();
    
    public static final DataType<Number> NUMBER = new DataType<Number>("number", new float[] { 1F, 1F, 0F })
    {
        @Override
        public boolean acceptStrings()
        {
            return true;
        }
        
        @Override
        public boolean canParseString(String s)
        {
            return DataType.FLOAT.canParseString(s); // Float can handle both integer and float values.
        }
        
        @Override
        public Number stringToValue(String s)
        {
            return DataType.INTEGER.canParseString(s) ? DataType.INTEGER.stringToValue(s) : DataType.FLOAT.stringToValue(s); // Float can handle both integer and float values.
        }
    }.setDefaultValue(DataType.FLOAT.getDefaultValue()).setBlackText();
    
    public static final DataType<Boolean> BOOLEAN = new DataType<Boolean>("boolean", new float[] { 1F, 0F, 1F })
    {
        @Override
        public boolean acceptStrings()
        {
            return true;
        }
        
        @Override
        public boolean canParseString(String s)
        {
            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        }
        
        @Override
        public Boolean stringToValue(String s)
        {
            return Boolean.valueOf(s);
        }
    }.addEnum(true).addEnum(false);
    
    static
    {
        DataType.NUMBER.registerConverter(DataType.FLOAT, new FloatNumber()); // "But int can be casted to Number! Why not use the generic one?"
        DataType.NUMBER.registerConverter(DataType.INTEGER, new IntegerNumber()); // - Well, yes. But only the Integer type, not the "small" int. As I dont know how this will be used in the future, I rather add this in.
        
        DataType.FLOAT.registerGenericConverter(DataType.INTEGER); // int can be casted to float, so generic converter
        DataType.FLOAT.registerConverter(DataType.NUMBER, new NumberFloat()); // here Number#floatValue() should be used, so not a generic converter
        
        DataType.INTEGER.registerConverter(DataType.NUMBER, new NumberInteger()); // here Number#intValue() should be used, so not a generic converter
    }
    
    /**
     * All registered converters
     */
    protected HashMap<DataType, Converter> converters;
    
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
    
    public DataType(String id)
    {
        this(id, new float[] { 0.5F, 0.5F, 0.5F });
    }
    
    public DataType(String id, float[] color)
    {
        this.id = id;
        this.converters = new HashMap<DataType, Converter>();
        this.color = color;
        this.textColor = DataType.COLOR_TEXT_WHITE;
        
        if (DataType.DATA_TYPES_LIST.containsKey(id))
        {
            Visibilis.error("Data type \"" + id + "\" already exists!");
        }
        
        DataType.DATA_TYPES_LIST.put(id, this);
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
    public /* final */ DataType registerConverter(DataType from, Converter converter)
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
    public /* final */ DataType registerGenericConverter(DataType from)
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
    public boolean canConvert(DataType from)
    {
        return from == this || this.converters.containsKey(from);
    }
    
    /**
     * Converts the value of this node field to this data type; {@link #canConvert(DataType)} for the node field's data type must be true otherwise NullPointer is thrown.
     */
    public A convert(NodeField from)
    {
        return this.convert(from.dataType, from.getValue());
    }
    
    /**
     * Converts the value which represents the given data type to this data type; {@link #canConvert(DataType)} for the given data type must be true otherwise NullPointer is thrown.
     */
    public A convert(DataType from, Object value)
    {
        return this.converters.get(from).<A> convert(value);
    }
    
    /*
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     */
    
    /*
     * The following is used primarily for Guis, but also fields in general. This allows (as example):
     * - A default value for non-connected inputs to use
     * - A box to be shown in Gui to either
     *   - Show an input field to type a value
     *   - Show a drop down for a value to choose from
     * 
     * Dynamic: This means that the values can all be parsed from a String. Eg. Any integer value.
     * - It requires:
     *   - #acceptStrings() to return true (override)
     *   - #setDefaultValue(A) to be set (chain on instantiation)
     *   - #canParseString(String) to be implemented (override)
     *   - #stringToValue(String) to be implemented (override)
     * 
     * enum: this means you have a set list of possibilities to choose from. Eg. Booleans (either "true" or "false", nothing else).
     * - It requires:
     *   - #addEnum(A) to be called for each possibility (chain on instantiation)
     * 
     * For any non primitive data type, #valueToString(A) should also be overridden either in the data type class or in the passed generic class (A).
     * You can choose yourself which of these 2 options fit your data type better. But if there are too many possibilities, then the Dynamic one should be better.
     * 
     * This is all not required for a data type anyways. Sometimes it is definitely better NOT to implement this with very advanced types (eg. a list type).
     */
    
    // --- Dynamic & enum start ---
    
    /**
     * The default value for unconnected inputs
     */
    protected A defaultValue = null;
    
    /**
     * List of all possible values this data type can have. Used for enums and eg. booleans.
     */
    protected ArrayList<A> enums = null;
    
    /**
     * Returns the default value. If {@link #isEnum()} is <b>true</b> then returns the first enum instead. This default value will be used for inputs without connection.
     * 
     * @see #setDefaultValue(Object)
     */
    public A getDefaultValue()
    {
        return this.isEnum() ? this.getDefaultEnum() : this.defaultValue;
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
    
    public String valueToString(A value)
    {
        return value.toString();
    }
    
    // --- Dynamic & enum end
    // --- Dynamic start ---
    
    /**
     * If <b>true</b> inputs can be typed on the Gui. It will deliver a box the user can type in. Requires a default value to be displayed first.
     * 
     * @see #setDefaultValue(Object)
     */
    public boolean acceptStrings()
    {
        return false;
    }
    
    /**
     * Set the default value. If {@link #addEnum(Object)} has been used this method has no effect. This default value will be used for inputs without connection.
     */
    public DataType setDefaultValue(A value)
    {
        this.defaultValue = value;
        return this;
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
    
    public boolean canParseString(String s)
    {
        return false;
    }
    
    public A stringToValue(String s)
    {
        return null;
    }
    
    // --- Dynamic end ---
    // --- enum start ---
    
    /**
     * Adds a possible value to this data type. Once a single enum has been set, this data type is effectively an enum and should only be represented by values added by this method. On a Gui, this will show a dropdown of possibilities for the user to choose from, instead of a string input.
     */
    public DataType addEnum(A value)
    {
        if (this.enums == null)
        {
            this.enums = new ArrayList<A>();
        }
        
        this.enums.add(value);
        return this;
    }
    
    /**
     * Returns <b>true</b> if {@link #addEnum(Object)} has been used.
     */
    public boolean isEnum()
    {
        return this.enums != null;
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
        for (int i = 0; i < this.enums.size(); ++i)
        {
            if (this.enums.get(i) == value)
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
    
    // --- enum end ---
    
    /*
     * --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
     */
    
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
    
    public int getTextColorInt()
    {
        int r = (int) (this.getTextColor()[0] * 255F);
        int g = (int) (this.getTextColor()[1] * 255F);
        int b = (int) (this.getTextColor()[2] * 255F);
        int a = 255;
        
        int color = (a << 24);
        color = color | (r << 16);
        color = color | (g << 8);
        color = color | (b);
        
        return color;
    }
    
    public DataType setTextColor(float[] color)
    {
        this.textColor = color;
        return this;
    }
    
    public DataType setBlackText()
    {
        return this.setTextColor(DataType.COLOR_TEXT_BLACK);
    }
}
