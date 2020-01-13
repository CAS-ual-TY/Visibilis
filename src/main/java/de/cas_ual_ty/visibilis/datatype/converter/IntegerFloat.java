package de.cas_ual_ty.visibilis.datatype.converter;

import de.cas_ual_ty.visibilis.datatype.Converter;

public class IntegerFloat extends Converter<Integer, Float>
{
    @Override
    public Float convert(Integer value)
    {
        return value.floatValue();
    }
}
