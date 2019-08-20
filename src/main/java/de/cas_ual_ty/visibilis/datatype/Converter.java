package de.cas_ual_ty.visibilis.datatype;

public class Converter
{
    public VDataType from;
    public VDataType to;

    public Converter()
    {
    }

    public Converter setFromTo(VDataType from, VDataType to)
    {
        this.from = from;
        this.to = to;
        return this;
    }

    public <A> A convert(Object value)
    {
        return (A) value;
    }
}
