package de.cas_ual_ty.visibilis.datatype;

public abstract class DataTypeDynamic<A> extends DataType<A>
{
    // Default value is required for this type of a data type so it is added to the constructor.
    
    public DataTypeDynamic(String id, A defaultValue)
    {
        this(id, DataType.COLOR_DEFAULT_GREY, defaultValue);
    }
    
    public DataTypeDynamic(String id, float[] color, A defaultValue)
    {
        super(id, color);
        this.setDefaultValue(defaultValue);
    }
    
    /**
     * Can this string be parsed to a value in {@link #stringToValue(String)} (then return <b>true</b>)?
     */
    public abstract boolean canParseString(String s);
    
    /**
     * Turn a string into a value
     */
    public abstract A stringToValue(String s);
}
