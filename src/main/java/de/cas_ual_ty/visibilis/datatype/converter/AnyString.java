package de.cas_ual_ty.visibilis.datatype.converter;

import de.cas_ual_ty.visibilis.datatype.Converter;

public class AnyString<A> extends Converter<A, String>
{
    @Override
    public String convert(Object value)
    {
        return value.toString();
    }
}
