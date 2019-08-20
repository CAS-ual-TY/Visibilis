package de.cas_ual_ty.visibilis.datatype;

import java.util.HashMap;
import java.util.Map;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.NodeField;

public class VDataType<A>
{
    public static final VDataType EXEC = new VDataType("exec", new float[] { 1F, 1F, 1F });
    public static final VDataType<Float> FLOAT = new VDataType<Float>("float", new float[] { 1F, 1F, 0F });
    public static final VDataType<Integer> INTEGER = new VDataType<Integer>("integer", new float[] { 1F, 1F, 0F });
    public static final VDataType<Boolean> BOOLEAN = new VDataType<Boolean>("boolean", new float[] { 1F, 0F, 1F });

    public static final Map<String, VDataType> DATA_TYPES_LIST = new HashMap<String, VDataType>();

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
            return this;
        }

        converter.setFromTo(from, this);
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
     * Converts the value which represents the given data type to this data type; {@link #canConvert(VDataType)} for the given data type must be true otherwise NullPointer is
     * thrown.
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
        return new float[] { 1F, 1F, 1F };
    }
}
