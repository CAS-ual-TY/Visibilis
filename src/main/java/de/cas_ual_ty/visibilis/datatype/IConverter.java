package de.cas_ual_ty.visibilis.datatype;

public interface IConverter<F, T>
{
    public T convert(F value);
}
