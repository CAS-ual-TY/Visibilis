package de.cas_ual_ty.visibilis.datatype.converter;

public class AnyString<A> implements IConverter<A, String>
{
    @Override
    public String convert(Object value)
    {
        return value.toString();
    }
}
