package de.cas_ual_ty.visibilis.datatype;

import de.cas_ual_ty.visibilis.util.VUtility;

public class Converter<F, T>
{
    public DataType<F> from;
    public DataType<T> to;
    
    public Converter()
    {
    }
    
    public Converter<F, T> setFromTo(DataType<F> from, DataType<T> to)
    {
        this.from = from;
        this.to = to;
        return this;
    }
    
    public T convert(F value)
    {
        return VUtility.cast(value);
    }
}
