package de.cas_ual_ty.visibilis.datatype.converter;

public class ObjToStringConverter<A> implements IConverter<A, String>
{
    @Override
    public String convert(A value)
    {
        return value.toString();
    }
}
