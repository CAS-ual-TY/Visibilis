package de.cas_ual_ty.visibilis.datatype.converter;

import de.cas_ual_ty.visibilis.datatype.Converter;

public class AnyString extends Converter
{
    @Override
    public <A> A convert(Object value)
    {
        return (A) String.valueOf(value);
    }
}
