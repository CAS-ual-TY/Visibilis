package de.cas_ual_ty.visibilis.datatype;

public class Converter
{
    public DataType<?> from;
    public DataType<?> to;
    
    public Converter()
    {
    }
    
    public Converter setFromTo(DataType<?> from, DataType<?> to)
    {
        this.from = from;
        this.to = to;
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public <A> A convert(Object value)
    {
        return (A) value;
    }
}
