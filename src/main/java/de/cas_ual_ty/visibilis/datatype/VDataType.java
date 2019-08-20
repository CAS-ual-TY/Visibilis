package de.cas_ual_ty.visibilis.datatype;

import java.util.HashMap;
import java.util.Map;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.converter.FloatNumber;
import de.cas_ual_ty.visibilis.datatype.converter.IntegerNumber;
import de.cas_ual_ty.visibilis.datatype.converter.NumberFloat;
import de.cas_ual_ty.visibilis.datatype.converter.NumberInteger;
import de.cas_ual_ty.visibilis.node.NodeField;

public class VDataType<A>
{
    public static final Map<String, VDataType> DATA_TYPES_LIST = new HashMap<String, VDataType>();
    
    public static final VDataType EXEC = new VDataType("exec", new float[] { 1F, 1F, 1F });
    public static final VDataType<Number> NUMBER = new VDataType<Number>("number", new float[] { 1F, 1F, 0F });
    public static final VDataType<Float> FLOAT = new VDataType<Float>("float", new float[] { 1F, 0.5F, 0F });
    public static final VDataType<Integer> INTEGER = new VDataType<Integer>("integer", new float[] { 0.5F, 1F, 0F });
    public static final VDataType<Boolean> BOOLEAN = new VDataType<Boolean>("boolean", new float[] { 1F, 0F, 1F });
    
    static
    {
        NUMBER.registerConverter(FLOAT, new FloatNumber()); // "But int can be casted to Number! Why not use the generic one?"
        NUMBER.registerConverter(INTEGER, new IntegerNumber()); // - Well, yes. But only the Integer type, not the "small" int. As I dont know how this will be used in the future, I rather add this in.
        
        FLOAT.registerGenericConverter(INTEGER); // int can be casted to float, so generic converter
        FLOAT.registerConverter(NUMBER, new NumberFloat()); // here Number#floatValue() should be used, so not a generic converter
        
        INTEGER.registerConverter(NUMBER, new NumberInteger()); // here Number#intValue() should be used, so not a generic converter
    }
    
    /**
     * All registered converters
     */
    protected HashMap<VDataType, Converter> converters;
    
    /**
     * Unique id. Used for translation
     */
    public final String id;
    
    /**
     * Color for rendering
     */
    protected float[] color;
    
    public VDataType(String id)
    {
        this(id, new float[] { 0.5F, 0.5F, 0.5F });
    }
    
    public VDataType(String id, float[] color)
    {
        this.id = id;
        this.converters = new HashMap<VDataType, Converter>();
        this.color = color;
        
        if (DATA_TYPES_LIST.containsKey(id))
        {
            Visibilis.error("Data type \"" + id + "\" already exists!");
        }
        
        DATA_TYPES_LIST.put(id, this);
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
    public /* final */ VDataType registerConverter(VDataType from, Converter converter)
    {
        if (this == EXEC || from == EXEC)
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
    public /* final */ VDataType registerGenericConverter(VDataType from)
    {
        if (this == EXEC || from == EXEC)
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
    public boolean canConvert(VDataType from)
    {
        return this.converters.containsKey(from);
    }
    
    /**
     * Converts the value of this node field to this data type; {@link #canConvert(VDataType)} for the node field's data type must be true otherwise NullPointer is thrown.
     */
    public A convert(NodeField from)
    {
        return this.convert(from.dataType, from.getValue());
    }
    
    /**
     * Converts the value which represents the given data type to this data type; {@link #canConvert(VDataType)} for the given data type must be true otherwise NullPointer is thrown.
     */
    public A convert(VDataType from, Object value)
    {
        return this.converters.get(from).<A> convert(value);
    }
    
    /**
     * Get the color of this data type, for connections and node field dots
     * 
     * @return
     */
    public float[] getColor()
    {
        return this.color;
    }
}
